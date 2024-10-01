package com.prologApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

/**
 * Интерфейс взаимодействия с Prolog (Prolog Query Layer)
 */
public class PrologInterface {

    public final String FILE = "req.pl";

    /**
     * Инициализация SWIPL engine
     */
    public PrologInterface() {
        if (System.getenv("SWI_HOME_DIR") != null
                || System.getenv("SWI_EXEC_FILE") != null
                || System.getenv("SWIPL_BOOT_FILE") != null) {
            String init_swi_config
                    = String.format("%s %s %s -g true -q --no-signals --no-packs",
                            System.getenv("SWI_EXEC_FILE") == null ? "swipl"
                            : System.getenv("SWI_EXEC_FILE"),
                            System.getenv("SWIPL_BOOT_FILE") == null ? ""
                            : String.format("-x %s", System.getenv("SWIPL_BOOT_FILE")),
                            System.getenv("SWI_HOME_DIR") == null ? ""
                            : String.format("--home=%s", System.getenv("SWI_HOME_DIR")));
            //String.format("\nSWIPL initialized with: %s", init_swi_config)
            JPL.setDefaultInitArgs(init_swi_config.split("\\s+"));
        } else {
            throw new IllegalStateException("Initialization failed: No SWI_HOME_DIR, SWI_EXEC_FILE, or SWIPL_BOOT_FILE defined.");
        }
        JPL.setTraditional();
        JPL.init();
        //"Prolog engine actual init args: " + Arrays.toString(Prolog.get_actual_init_args())
        if (!checkQueryProlog(this.FILE)) {
            throw new IllegalStateException("Запрос к файлу произошёл с ошибкой");
        }
    }

    /**
     * Проверка работоспособности БЗ
     *
     * @param namePl
     * @return bool прошел запрос
     */
    private Boolean checkQueryProlog(String namePl) {
        Query q1 = new Query(
                "consult",
                new Term[]{new Atom(namePl)}
        );
        return q1.hasSolution();
    }

    /**
     * Создание и выполнение запроса к БЗ на основе термов пользователя
     *
     * @param userTerms
     * @throws NullPointerException
     */
    public void queryUserPref(List<? extends String> userTerms) throws NullPointerException {
        List<Term> terms = userTerms.stream().map(term -> new Atom(term)).collect(Collectors.toList());

        Variable Server = new Variable("Server");

        Query query = new Query(
                "server_for_user",
                new Term[]{Server, terms.get(0), terms.get(1), terms.get(2)}
        );

        if (query.hasSolution()) {
            displayResult(query.allSolutions());
        } else {
            throw new NullPointerException("Не удалось найти подходящий сервер.");
        }
    }

    /**
     * Отображение результатов поиска серверов
     *
     * @param solutions
     */
    public void displayResult(Map<String, Term>[] solutions) {
        for (Map<String, Term> solution : solutions) {
            String serv = solution.get("Server").name();
            Variable IP = new Variable("IP");
            Query queryIP = new Query(
                    "server_ip",
                    new Term[]{new Atom(serv), IP}
            );
            System.out.println("Сервер: " + serv + "\n\t" + queryIP.oneSolution());
        }
    }

    /**
     * Правила для корректной работы запросов к БЗ
     *
     * @return Список правил
     */
    public static String getRules() {
        return "Данные принимаются в формате:\n\n"
                + "<режим игры>; <стиль игры>; <карта сервера>\n \n"
                + "\t<режим игры> - может быть либо 2pp (игра от первого лица), либо 3pp (игра от тертьего лица)\n"
                + "\t<стиль игры> - предпочитаемый вами стиль игры. Режимы входящие в БЗ: pvp, pve, lite, rp\n"
                + "\t<карта сервера> - карта, на которой будет комфортнее играть. Карты входящие в БЗ: chernarus_plus, enoch, livonia, nmchernobyl\n\n"
                + "По итогу вашего запроса будут выведены наиболее подходящие сервера.\n\n";
    }
}
