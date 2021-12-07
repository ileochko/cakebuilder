package ru.sfedu.cakebuilder.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.cakebuilder.api.DataProviderCsv;
import ru.sfedu.cakebuilder.models.Cake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class  CakeListConverter extends AbstractBeanField<Cake, Integer> {
    private static final DataProviderCsv DATA_PROVIDER = new DataProviderCsv();

    @Override
    protected Object convert(String s) {
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedList = indexString.split(",");
        List<String> indexTaskList = new ArrayList<>(Arrays.asList(unparsedList));
        List<Cake> customCakeList = new ArrayList<>();
        indexTaskList.forEach(indexStr -> customCakeList.add(DATA_PROVIDER.getCustomCakeByID(Long.parseLong(indexStr)).get()));
        return customCakeList;
    }

    public String convertToWrite(Object value) {
        List<Cake> customCakeList = (List<Cake>) value;
        StringBuilder builder = new StringBuilder("[");
        if (customCakeList.size() > 0) {
            customCakeList.forEach(customCake -> builder
                    .append(customCake.getId())
                    .append(","));
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        return builder.toString();
    }
}
