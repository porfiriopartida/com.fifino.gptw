package com.fifino.gptw.helpers.db;

import org.junit.Test;

/**
 * Created by porfiriopartida on 6/14/2016.
 */
public class DFContractTest {
    @Test
    public void testCreateTables(){
        String scoresTable = DFContract.ScoresTable.getCreateTable();
        System.out.println(scoresTable);
    }
}