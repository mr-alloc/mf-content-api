package com.cofixer.mf.mfcontentapi.configuration;

import kr.devis.util.entityprinter.print.PrintConfigurator;
import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableSetting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityPrinterConfiguration {


    @Bean
    public EntityPrinter printer() {
        return new EntityPrinter();
    }

    @Bean
    public PrintConfigurator<Integer> ec () {
        return ExpandableSetting.EXPANDABLE_ENTITY_SETTING
                .withoutFloor()
                .getConfig();
    }

    @Bean
    public PrintConfigurator<String> mc () {
        return ExpandableSetting.EXPANDABLE_MAP_SETTING
                .withoutFloor()
                .getConfig();
    }

}
