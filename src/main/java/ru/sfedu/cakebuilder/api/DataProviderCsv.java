package ru.sfedu.cakebuilder.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.cakebuilder.Answer;
import ru.sfedu.cakebuilder.Constants;
import ru.sfedu.cakebuilder.models.*;
import ru.sfedu.cakebuilder.models.enums.Status;
import ru.sfedu.cakebuilder.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.sfedu.cakebuilder.Constants.ID_NOT_EXISTS;
import static ru.sfedu.cakebuilder.models.enums.Status.*;
import static ru.sfedu.cakebuilder.utils.HistoryUtil.saveToLog;

public class DataProviderCsv implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);

    @Override
    public Answer<Integer> getIngredientPrice(String typeOfPrice) {
        try {
            if (typeOfPrice.isEmpty()) {
                return new Answer<>(EnterError);
            }
            switch (typeOfPrice.toUpperCase()) {
                case "COMMON":
                    return new Answer(Complete, calculateIngredientCost().getData());
                case "DETAIL":
                    return new Answer(Complete, getDetailedCheck().getData());
                default:
                    return new Answer<>(EnterError);
            }
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }


    @Override
    public Answer<List<Ingredient>> getDetailedCheck() {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Ingredient> listRes = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
            return new Answer<>(Complete, listRes);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> getProfit() {
        try {
            int spended = getIngredientPrice("Common").getData();
            int earned = summariseAllProfit().getData();
            return new Answer<>(Complete, earned - spended);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> summariseAllProfit() {
        try {
            int earned = 0;
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<CustomCake> customCakeList = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
            List<SpecialCake> specialCakeList = csvToBean(SpecialCake.class, Constants.SPECIAL_CAKE_CSV, method);
            for (CustomCake customCake : customCakeList) {
                earned = earned + customCake.getPrice();
            }
            for (SpecialCake specialCake : specialCakeList) {
                earned = earned + specialCake.getPrice();
            }
            return new Answer<>(Complete, earned);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> calculateCustomCakeCost(Cake cake) {
        try {
            switch (cake.getClass().getSimpleName()) {
                case "CustomCake":
                    int price = 0;
                    price += getIngredientCost((CustomCake) cake).getData();
                    price += getChiefTime(cake.getNeedDecorate()).getData();
                    return new Answer<>(Complete, price);
                case "SpecialCake":
                    specialOrder((SpecialCake) cake);
                default:
                    return new Answer<>(UnkonwmClass);
            }
        } catch (
                Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> specialOrder(SpecialCake cake) {
        try {
            switch (cake.getBasicCake()) {
                case EXTRA:
                    return new Answer<>(Complete, Constants.EXTRA);
                case CORONA:
                    return new Answer<>(Complete, Constants.CORONA);
                case HONEYPIE:
                    return new Answer<>(Complete, Constants.HONEYPIE);
                case NAPOLEON:
                    return new Answer<>(Complete, Constants.NAPOLEON);
                default:
                    return new Answer<>(UnkonwmClass);
            }
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> getChiefTime(Boolean isNeedDecorate) {
        try {
            int chiefPrice = 0;
            int finalChiefPrice = isNeedDecorate ? chiefPrice + Constants.ADDITIONAL_PRICE : chiefPrice + Constants.BASE_PRICE;
            return new Answer<>(Complete, finalChiefPrice);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public Answer<Integer> getIngredientCost(CustomCake cake) {
        try {
            int price = 0;
            for (int i = 0; i < cake.getIngredients().size(); i++) {
                price += cake.getIngredients().get(i).getPrice() * cake.getIngredients().get(i).getNumberOfIngredient();
            }
            return new Answer<>(Complete, price);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }

    @Override
    public <T extends Cake> Answer<Cake> getCakes(long id) {
        List<Cake> cakeList = new ArrayList<>();
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> customCakeList = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
        List<SpecialCake> specialCakeList = csvToBean(SpecialCake.class, Constants.SPECIAL_CAKE_CSV, method);
        cakeList.addAll(customCakeList);
        cakeList.addAll(specialCakeList);
        Optional<Cake> optCake = cakeList.stream().filter(el -> el.getId() == id).findAny();
        return optCake.map(task -> new Answer<>(Complete, task)).orElseGet(() ->
                new Answer<>(Fail));
    }

    @Override
    public Answer<Ingredient> getIngredients(long parseLong) {
        List<Ingredient> cakeList = new ArrayList<>();
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Ingredient> customCakeList = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
        cakeList.addAll(customCakeList);
        Optional<Ingredient> optIngred = cakeList.stream().filter(el -> el.getId() == parseLong).findAny();
        return optIngred.map(task -> new Answer<>(Complete, task)).orElseGet(() ->
                new Answer<>(Fail));
    }

    @Override
    public Answer<Order> insertOrder(Order order) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> orderList = csvToBean(Order.class, Constants.ORDER_CSV, method);
        if (orderList.stream().anyMatch(o -> o.getId() == order.getId())) {
            return new Answer<>(Fail, order);
        }
        orderList.add(order);
        if (beanToCsv(orderList, Constants.ORDER_CSV, method) == Fail) {
            return new Answer<>(Fail, order);
        }
        return new Answer<>(Success, order);
    }

    @Override
    public Answer<Void> updateOrder(Order order) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.ORDER_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == order.getId())) {
            return new Answer(Fail, order, format(ID_NOT_EXISTS, order.getId()));
        }
        objects.removeIf(o -> o.getId() == order.getId());
        objects.add(order);
        if (beanToCsv(objects, Constants.ORDER_CSV, method) == Fail) {
            return new Answer(Fail, order, null);
        }
        return new Answer(Success, order, null);
    }

    @Override
    public Optional<Order> searchOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.ORDER_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Answer<Void> deleteOrder(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.ORDER_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.ORDER_CSV, method);
        return new Answer<>(Success);
    }

    @Override
    public Answer<Ingredient> insertIngredient(Ingredient ingredient) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Ingredient> orderList = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
        if (orderList.stream().anyMatch(o -> o.getId() == ingredient.getId())) {
            return new Answer<>(Fail, ingredient);
        }
        orderList.add(ingredient);
        if (beanToCsv(orderList, Constants.INGREDIENTS_CSV, method) == Fail) {
            return new Answer<>(Fail, ingredient);
        }
        return new Answer<>(Success, ingredient);
    }

    @Override
    public Answer<Void> updateIngredient(Ingredient ingredient) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Ingredient> objects = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == ingredient.getId())) {
            return new Answer(Fail, ingredient, format(ID_NOT_EXISTS, ingredient.getId()));
        }
        objects.removeIf(o -> o.getId() == ingredient.getId());
        objects.add(ingredient);
        if (beanToCsv(objects, Constants.INGREDIENTS_CSV, method) == Fail) {
            return new Answer(Fail, ingredient, null);
        }
        return new Answer(Success, ingredient, null);
    }

    @Override
    public Optional<Ingredient> searchIngredientById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Ingredient> objects = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Answer<Void> deleteIngredient(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Ingredient> objects = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.INGREDIENTS_CSV, method);
        return new Answer<>(Success);
    }

    @Override
    public Answer<Cake> insertCake(Cake element) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cake> customCakeList = csvToBean(Cake.class, Constants.CAKE_CSV, method);
        if (customCakeList.stream().anyMatch(o -> o.getId() == element.getId())) {
            return new Answer<>(Fail, element);
        }
        customCakeList.add(element);
        if (beanToCsv(customCakeList, Constants.CAKE_CSV, method) == Fail) {
            return new Answer<>(Fail, element);
        }
        return new Answer<>(Success, element);
    }

    @Override
    public Answer<Void> updateCake(Cake updElement) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cake> objects = csvToBean(Cake.class, Constants.CAKE_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == updElement.getId())) {
            return new Answer(Fail, updElement, format(ID_NOT_EXISTS, updElement.getId()));
        }
        objects.removeIf(o -> o.getId() == updElement.getId());
        objects.add(updElement);
        if (beanToCsv(objects, Constants.CAKE_CSV, method) == Fail) {
            return new Answer(Fail, updElement, null);
        }
        return new Answer(Success, updElement, null);
    }

    @Override
    public Optional<Cake> getCakeByID(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cake> objects = csvToBean(Cake.class, Constants.CAKE_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Answer<Void> deleteCake(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cake> objects = csvToBean(Cake.class, Constants.CAKE_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.CAKE_CSV, method);
        return new Answer<>(Success);
    }

    @Override
    public Answer<CustomCake> insertCustomCake(CustomCake element) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> customCakeList = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
        if (customCakeList.stream().anyMatch(o -> o.getId() == element.getId())) {
            return new Answer<>(Fail, element);
        }
        customCakeList.add(element);
        if (beanToCsv(customCakeList, Constants.CUSTOM_CAKE_CSV, method) == Fail) {
            return new Answer<>(Fail, element);
        }
        return new Answer<>(Success, element);
    }

    @Override
    public Answer<SpecialCake> insertSpecialCake(SpecialCake element) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<SpecialCake> specialCakeList = csvToBean(SpecialCake.class, Constants.SPECIAL_CAKE_CSV, method);
        if (specialCakeList.stream().anyMatch(o -> o.getId() == element.getId())) {
            return new Answer<>(Fail, element);
        }
        specialCakeList.add(element);
        if (beanToCsv(specialCakeList, Constants.SPECIAL_CAKE_CSV, method) == Fail) {
            return new Answer<>(Fail, element);
        }
        return new Answer<>(Success, element);
    }

    @Override
    public Answer<Void> updateCustomCake(CustomCake updElement) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> objects = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == updElement.getId())) {
            return new Answer(Fail, updElement, format(ID_NOT_EXISTS, updElement.getId()));
        }
        objects.removeIf(o -> o.getId() == updElement.getId());
        objects.add(updElement);
        if (beanToCsv(objects, Constants.CUSTOM_CAKE_CSV, method) == Fail) {
            return new Answer(Fail, updElement, null);
        }
        return new Answer(Success, updElement, null);
    }

    @Override
    public Answer<Void> updateSpecialCake(SpecialCake updElement) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<SpecialCake> objects = csvToBean(SpecialCake.class, Constants.SPECIAL_CAKE_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == updElement.getId())) {
            return new Answer(Fail, updElement, format(ID_NOT_EXISTS, updElement.getId()));
        }
        objects.removeIf(o -> o.getId() == updElement.getId());
        objects.add(updElement);
        if (beanToCsv(objects, Constants.SPECIAL_CAKE_CSV, method) == Fail) {
            return new Answer(Fail, updElement, null);
        }
        return new Answer(Success, updElement, null);
    }

    @Override
    public Optional<CustomCake> getCustomCakeByID(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> objects = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Optional<SpecialCake> getSpecialCakeByID(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<SpecialCake> objects = csvToBean(SpecialCake.class, Constants.SPECIAL_CAKE_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Answer<Void> deleteCustomCake(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> objects = csvToBean(CustomCake.class, Constants.CUSTOM_CAKE_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.CUSTOM_CAKE_CSV, method);
        return new Answer<>(Success);
    }

    @Override
    public Answer<Void> deleteSpecialCake(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomCake> objects = csvToBean(CustomCake.class, Constants.SPECIAL_CAKE_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.SPECIAL_CAKE_CSV, method);
        return new Answer<>(Success);
    }

    private static HistoryContent createHistoryContent(String method, Object object, Status status) {
        return new HistoryContent(DataProviderCsv.class.getSimpleName(), new Date(), Constants.DEFAULT_ACTOR, method, object, status);
    }

    private <T> Status beanToCsv(List<T> ts, String key, String method) {
        Status status;
        try {
            FileWriter fileWriter = new FileWriter(ConfigurationUtil.getConfigurationEntry(key), false);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(ts);
            csvWriter.close();
            fileWriter.close();
            status = Success;
        } catch (Exception exception) {
            log.error(exception);
            status = Fail;
        }
        saveToLog(createHistoryContent(method, ts, status));
        return status;
    }

    public static <T> List<T> csvToBean(Class<T> cls, String key, String method) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(ConfigurationUtil.getConfigurationEntry(key)));
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(cls).build();
            List<T> querySet = csvToBean.parse();
            csvReader.close();
            saveToLog(createHistoryContent(method, querySet, Success));
            return querySet;
        } catch (Exception exception) {
            log.error(exception);
        }
        saveToLog(createHistoryContent(method, null, Fail));
        return new ArrayList<>();
    }

    private Answer<Integer> calculateIngredientCost() {
        try {
            int price = 0;
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Ingredient> listRes = csvToBean(Ingredient.class, Constants.INGREDIENTS_CSV, method);
            for (int i = 0; i < listRes.size(); i++) {
                price = price + listRes.get(i).getPrice() * listRes.get(i).getNumberOfIngredient();
            }
            return new Answer<>(Complete, price);
        } catch (Exception e) {
            log.error(e);
            return new Answer<>(Fail);
        }
    }
}
