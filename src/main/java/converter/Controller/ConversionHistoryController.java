package converter.Controller;

import converter.Model.Conversion;
import converter.service.ConversionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ConversionHistoryController {
    @Autowired
    private ConversionHistoryService conversionHistoryService;

    @GetMapping("/history")
    public String main(Model model) {
        List<Conversion> conversions = conversionHistoryService.getConversions();
        model.addAttribute("conversions", conversions);
        return "history";
    }
}
