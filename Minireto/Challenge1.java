import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Challenge1
 */
public class Challenge1 {

    public static void main(String[] args) {
        System.out.println("Tipo A");
        System.out.println(chainGeneratorString("Tipo A"));
        System.out.println("Tipo B");
        System.out.println(chainGeneratorString("Tipo b"));
        List<String> list = new ArrayList<String>();
        String val1 = chainGeneratorString("Tipo A");
        String val2 = chainGeneratorString("Tipo b");
        String val3 = chainGeneratorString("Tipo A");

        System.out.println(ifExist(val1, list));
        System.out.println(ifExist(val2, list));
        System.out.println(ifExist(val3, list));
        System.out.println(ifExist(val1, list));
        //el ultimo valor no deberia estar en la lista
        list.forEach(System.out::println);
    }

    public static String chainGeneratorString(String tipo) {
        String chain = "";
        if (tipo.toLowerCase().equals("tipo a")) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "54" + randNum;
        } else if (tipo.toLowerCase().equals("tipo b")) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "08" + randNum;
        }
        return chain;
    }

    public static boolean ifExist(String value, List<String> list) {

        if (!list.isEmpty()) {
            for (String aux : list) {
                if (aux.equals(value)) {
                    return false;
                }
            }
        }
        list.add(value);

        return true;
    }
}