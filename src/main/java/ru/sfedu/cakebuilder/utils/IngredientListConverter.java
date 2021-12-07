package ru.sfedu.cakebuilder.utils;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.cakebuilder.api.DataProviderCsv;
import ru.sfedu.cakebuilder.models.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientListConverter extends AbstractBeanField<Ingredient, Integer> {
    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);
    private static final DataProviderCsv DATA_PROVIDER = new DataProviderCsv();

    @Override
    protected Object convert(String s) {
        String indexString = s.substring(1, s.length() - 1);
        String[] unparsedList = indexString.split(",");
        List<String> indexTaskList = new ArrayList<>(Arrays.asList(unparsedList));
        List<Ingredient> ingredientList = new ArrayList<>();
        indexTaskList.forEach(indexStr -> ingredientList.add(DATA_PROVIDER.getIngredients(Long.parseLong(indexStr)).getData()));
        return ingredientList;
    }

    public String convertToWrite(Object value) {
        List<Ingredient> ingredientList = (List<Ingredient>) value;
        StringBuilder builder = new StringBuilder("[");
        if (ingredientList.size() > 0) {
            log.debug(ingredientList);
            ingredientList.forEach(ingredient -> builder
                    .append(ingredient.getId())
                    .append(","));
            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        return builder.toString();
    }
}
