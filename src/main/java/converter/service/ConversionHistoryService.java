package converter.service;

import converter.Model.Conversion;
import converter.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversionHistoryService {
    @Autowired
    private ConversionRepository conversionRepository;

    public void saveConversion(Conversion conversion){
        conversionRepository.save(conversion);
    }

    public List<Conversion> getConversions() {
        return conversionRepository.findAll();
    }
}
