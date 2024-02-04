package com.cofixer.mf.mfcontentapi.configuration;

import kr.devis.util.entityprinter.print.printer.EntityPrinter;
import kr.devis.util.entityprinter.print.setting.ExpandableEntitySetting;
import kr.devis.util.entityprinter.print.setting.ExpandableMapSetting;
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
    public ExpandableEntitySetting es() {
        return ExpandableSetting.EXPANDABLE_ENTITY_SETTING
                .withoutFloor()
                .excludeDataType();
    }

    @Bean
    public ExpandableMapSetting ms() {
        return ExpandableSetting.EXPANDABLE_MAP_SETTING
                .withoutFloor();
    }

}
