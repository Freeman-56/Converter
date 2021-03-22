package converter.Controller;

import converter.Model.Conversion;
import converter.Model.Valute;
import converter.service.ConversionHistoryService;
import converter.service.ValutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private static final String ERROR_MESSAGE = "Заполните правильно все поля";

    @Autowired
    private ValutesService valutesService;

    @Autowired
    private ConversionHistoryService conversionHistoryService;
    private List<Valute> valutes;
    private Map<Integer, Valute> valutesMap;


    @GetMapping
    public String main(Model model) {
        if (valutes == null)
            valutes = valutesService.getFreshValutes();
        if (valutes.size() == 0) {
            valutesService.updateValutes();
            valutes = valutesService.getFreshValutes();
        }
        valutesMap = valutes.stream().collect(Collectors.toMap(Valute::getId, valute -> valute));
        model.addAttribute("valutes", valutes);
        return "main";
    }


    @PostMapping
    public String convert(@RequestParam String valuteFromId,
                          @RequestParam String valuteToId,
                          @RequestParam String amount,
                          Model model) {
        Valute valuteFrom, valuteTo;
        if (valuteFromId.equals("Выберите валюту") || valuteToId.equals("Выберите валюту")
                || amount.equals("")
                || (!amount.matches("\\d+\\.\\d+") && !amount.matches("\\d+"))) {
            model.addAttribute("valutes", valutes);
            model.addAttribute("error", ERROR_MESSAGE);
            return "main";
        }
        valuteFrom = valutesMap.get(Integer.parseInt(valuteFromId));
        valuteTo = valutesMap.get(Integer.parseInt(valuteToId));
        double result = (Double.parseDouble(amount) * (valuteFrom.getValue() / valuteFrom.getNominal()))
                / (valuteTo.getValue() / valuteTo.getNominal());
        conversionHistoryService.saveConversion(new Conversion(valuteFrom, valuteTo, Double.parseDouble(amount), result, LocalDate.now()));
        model.addAttribute("valutes", valutes);
        model.addAttribute("result", String.format("%.2f", result));
        return "main";
    }
}
