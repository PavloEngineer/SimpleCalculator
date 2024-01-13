package com.example.kokhanevych3;

import static com.example.kokhanevych3.utils.Constants.SIGN_DIVISION;
import static com.example.kokhanevych3.utils.Constants.SIGN_MINUS;
import static com.example.kokhanevych3.utils.Constants.SIGN_MULTIPLYING;
import static com.example.kokhanevych3.utils.Constants.SIGN_PLUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Calculation {

    private static ArrayList<String> splittingExpression;

    private static final Map<Character, Function<double[], Double>> calculating = new HashMap<>();

    public Calculation() {
        initializationCalculatingForMap();
    }

    private void initializationCalculatingForMap() {
        calculating.put(SIGN_PLUS, (numbers) -> numbers[0] + numbers[1]);
        calculating.put(SIGN_MINUS, (numbers) -> numbers[0] - numbers[1]);
        calculating.put(SIGN_MULTIPLYING, (numbers) -> numbers[0] * numbers[1]);
        calculating.put(SIGN_DIVISION, (numbers) -> numbers[0] / numbers[1]);
    }

    public String calculateExpression(String expressing) {
        splittingExpression = new ArrayList<>();
        splitExpressing(expressing);
        calculateExpressionForOperation(SIGN_DIVISION);
        calculateExpressionForOperation(SIGN_MULTIPLYING);
        calculateExpressionForOperation(SIGN_MINUS);
        calculateExpressionForOperation(SIGN_PLUS);

        return splittingExpression.get(0);
    }

    private static void splitExpressing(String expressing) {
        StringBuilder number = new StringBuilder();
        number.append(expressing.charAt(0));
        for (int i = 1; i < expressing.length(); i++) {
            if (isSign(expressing.charAt(i) + "") && !isSign(expressing.charAt(i - 1) + "")) {
                splittingExpression.add(String.valueOf(number));
                splittingExpression.add(expressing.charAt(i) + "");
                number.delete(0, number.length());
            } else {
                number.append(expressing.charAt(i));
                if (i == expressing.length() - 1) {
                    splittingExpression.add(String.valueOf(number));
                }
            }
        }
    }

    private static void calculateExpressionForOperation(Character mathOperation) {
        Double result = 0.0;
        for (int j = 1; j < splittingExpression.size(); j++) {
            if (!isSign(splittingExpression.get(splittingExpression.size() - 1)) && Objects.equals(splittingExpression.get(j), mathOperation.toString())) {
                double[] values = new double[]{Double.parseDouble(splittingExpression.get(j - 1)), Double.parseDouble(splittingExpression.get(j + 1))};
                result = Objects.requireNonNull(calculating.get(mathOperation)).apply(values);

                splittingExpression.set(j - 1, String.valueOf(result));
                splittingExpression.remove(j + 1);
                splittingExpression.remove(j);
                j = 0;
            }
        }
    }

    private static boolean isSign(String word) {
        return word.equals(SIGN_PLUS.toString()) || word.equals(SIGN_MINUS.toString()) ||
                word.equals(SIGN_MULTIPLYING.toString()) || word.equals(SIGN_DIVISION.toString());
    }
}
