package Controllers;

import Models.BasePairs;

import java.util.ArrayList;
import java.util.Map;

public class ThreadController {
    int base = 5;
    int power = 3;

    public static void start() {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("You have: " + cores);

        for (Map.Entry<ArrayList<?>, Boolean> entry : BasePairs.basePair.entrySet()) {
            if ((entry.getKey().size() % 2) == 0 ) {
                System.out.println("Divides " + entry.getKey());
            } else {
                System.out.println("Not divides " + entry.getKey());
            }
        }
    }

    // 2**** -> (21*** 22*** 23*** 24***) <-> 5
    // 3**** -> (31*** 32*** 33*** 34*** 35*** 36***) <-> 7
    // | +--------+
    // +-| FOR 11 |---------------------------------------------------------------------------------------------+
    // | +--------+ [8 cores]                                                                                   |
    // | | 2****  | -> ((21*** 22***) (23*** 24***) (25*** 26***) (27*** 28***) || ((29***) (210***)))          |
    // | | 7****  | -> ((71*** 72***) (73*** 74***) (75*** 76***) (77*** 78***) || ((79***) (710***)))          |
    // | +--------+ [4 cores] +---------------------------------------------------------------------------------+
    // | | 2****  | -> ( (21*** 22*** 23***) (24*** 25*** 26***) || (27*** 28***) (29*** 210***))  |
    // | | 7****  | -> ( (71*** 72*** 73***) (74*** 75*** 76***) || (77*** 78***) (79*** 710***))  |
    // | +--------+                                                                                             |
    // +-| FOR 13 |---------------------------------------------------------------------------------------------+-------+
    // | +--------+ [8 cores]                                                                                           |
    // | | 2****  | -> ((21*** 22*** 23***) (24*** 25*** 26***) (27*** 28*** 29***) (2.10*** 2.11*** 2.12***))          |
    // | | 6****  | -> ((61*** 62*** 63***) (64*** 65*** 66***) (67*** 68*** 69***) (6.10*** 6.11*** 6.12***))          |
    // | +--------+ [4 cores] +-----------------------------------------------------------------------------------------+
    // | | 2****  | -> ( (21*** 22*** 23*** 24***) (25*** 26*** 27*** 28***) (29*** 2.10*** 2.11*** 2.12***))           |
    // | | 6****  | -> ( (61*** 62*** 63*** 64***) (65*** 66*** 67*** 68***) (69*** 6.10*** 6.11*** 6.12***))           |
    // + +--------+                                                                                                     |
    // +-| FOR 17 |------------------------------------------------------------------------------------------------------------------------------------------------------+
    // | +--------+ [8 cores]                                                                                                                                            |
    // | | 3****  | -> ( (31*** 32*** 33*** 34*** 35*** 36*** 37*** 38***) || (39*** 3.10*** 3.11*** 3.12*** 3.13*** 3.14*** 3.15*** 3.16***))                           |
    // | | 5****  | -> ( (51*** 52*** 53*** 54*** 55*** 56*** 57*** 58***) || (59*** 5.10*** 5.11*** 5.12*** 5.13*** 5.14*** 5.15*** 5.16***))                           |
    // | | 10**** | -> ( (10.1*** 10.2*** 10.3*** 10.4*** 10.5*** 10.6*** 10.7*** 10.8***) || (10.9*** 10.10*** 10.11*** 10.12*** 10.13*** 10.14*** 10.15*** 10.16***))  |
    // | | 11**** | -> ( (11.1*** 11.2*** 11.3*** 11.4*** 11.5*** 11.6*** 11.7*** 11.8***) || (11.9*** 11.10*** 11.11*** 11.12*** 11.13*** 11.14*** 11.15*** 11.16***))  |
    // | +--------+ [4 cores] +------------------------------------------------------------------------------------------------------------------------------------------+
    // | | 3****  | -> ( (31*** 32*** 33*** 34*** 35*** 36*** 37*** 38*** 39*** 3.10*** 3.11*** 3.12*** 3.13*** 3.14*** 3.15*** 3.16***))                                |
    // | | 5****  | -> ( (51*** 52*** 53*** 54*** 55*** 56*** 57*** 58*** 59*** 5.10*** 5.11*** 5.12*** 5.13*** 5.14*** 5.15*** 5.16***))                                |
    // | | 10**** | -> ( (10.1*** 10.2*** 10.3*** 10.4*** 10.5*** 10.6*** 10.7*** 10.8*** 10.9*** 10.10*** 10.11*** 10.12*** 10.13*** 10.14*** 10.15*** 10.16***))       |
    // | | 11**** | -> ( (11.1*** 11.2*** 11.3*** 11.4*** 11.5*** 11.6*** 11.7*** 11.8*** 11.9*** 11.10*** 11.11*** 11.12*** 11.13*** 11.14*** 11.15*** 11.16***))       |
    // + +--------+ +----------------------------------------------------------------------------------------------------------------------------------------------------+
    // n -> number of pairs, c -> number of cores, p-1 -> number of all possible variants
    // n = 4, c = 8, p-1 = 16, res = 2 for 8 cores and res = 1 for 4 cores
    //
    public static void calculate() {

    }
}