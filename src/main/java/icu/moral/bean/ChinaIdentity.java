package icu.moral.bean;

import com.google.common.base.Joiner;
import icu.moral.map.Area;
import icu.moral.util.TimeUtils;

public class ChinaIdentity {

    public ChinaIdentity(){
        number = randomIdentity();
        fillNumber(number);
    }

    public ChinaIdentity(String number){
        fillNumber(number);
    }

    private static final int[] _WEIGHT_ARRAY = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private static final char[] _VALIDATION_ARRAY = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private String number;

    private char[] _array;

    private char code = 'i';

    private Boolean validation = null;

    public boolean validate(){
        if(!completeCheck()){
            return false;
        }
        int sum = 0;

        if(validation == null){
            for (int i = 0 ; i < _array.length - 1 ; i++ ){
                sum += (_array[i] - 48) * _WEIGHT_ARRAY[i];
            }

            code = _VALIDATION_ARRAY[sum % 11];

            validation = code == _array[_array.length - 1];
        }

        return validation;
    }

    public char validationCode(){
        validate();
        return code;
    }

    public String IdentityNumber(){
        return number;
    }

    public ChinaIdentity fillNumber(String number){
        this.number = number;
        if(number != null){
            _array = number.toCharArray();
        }
        validation = null;
        code = 'i';
        return this;
    }

    private boolean completeCheck(){
        return number != null && _array != null && _array.length == 18;
    }

    private String randomIdentity() {

        StringBuilder builder = new StringBuilder(Joiner.on("").join(Area.getCode(), TimeUtils.birthTime(), (int)(Math.random() * 900) + 100, "$"));

        builder.append(new ChinaIdentity(builder.toString()).validationCode());

        return builder.toString().replace("$", "");
    }

}
