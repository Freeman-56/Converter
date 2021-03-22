package converter.service;

import converter.Model.Valute;
import converter.repository.ValuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ValutesService {
    @Autowired
    private ValuteRepository valuteRepository;

    private LocalDate nowDateFromCbr;
    private static final String URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";


    public void updateValutes() {
        List<Valute> newValutes = parseValutes();
        if (newValutes != null)
            valuteRepository.saveAll(newValutes);
    }

    private List<Valute> parseValutes() {
        try {
            LocalDate unparsedDate = LocalDate.now();
            String nowDate = unparsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL + nowDate);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ValCurs");
            LocalDate date = LocalDate.parse(nodeList.item(0).getAttributes().getNamedItem("Date").getNodeValue(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            nowDateFromCbr = date;

            nodeList = doc.getElementsByTagName("Valute");
            List<Valute> valutes = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
                    Integer nominal = Integer.valueOf(element.getElementsByTagName("Nominal").item(0).getTextContent());
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    NumberFormat format = NumberFormat.getInstance(Locale.FRENCH);
                    Double value = format.parse(element.getElementsByTagName("Value").item(0).getTextContent()).doubleValue();
                    Valute valute = new Valute(charCode, nominal, name, value, date);
                    valutes.add(valute);
                }
            }
            return valutes;
        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Valute> getFreshValutes() {
        if (nowDateFromCbr == null) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(URL + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("ValCurs");
                nowDateFromCbr = LocalDate.parse(nodeList.item(0).getAttributes().getNamedItem("Date").getNodeValue(),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return valuteRepository.findValutesByFreshDate(nowDateFromCbr);
    }
}
