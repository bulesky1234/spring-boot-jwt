package com.minrui.jwt.utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gale on 1/17/18.
 */

public class SqlGenerator {
    /*  data class CountResult(val count: Int, val data: List<*>)

      fun countDataFileds(fileds: String?, headerSql: String, tailSql: String, params: Map<String, *>, pageIndex: Int, pageSize: Int, entityManager: EntityManager): CountResult {
          val sqlAndParams = sql(headerSql, params)
          val sql = if (fileds != null && fileds.trim() != "") {
              "select $fileds " + sqlAndParams.first
          } else {
              sqlAndParams.first
          }
          val countQuery = entityManager.createQuery(countSql(sqlAndParams.first))
          val dataQuery = entityManager.createQuery(sql+" "+tailSql)
          sqlAndParams.second.forEachIndexed({ i, p ->
                  countQuery.setParameter(i, p)
                  dataQuery.setParameter(i, p)
          })
          val count = (countQuery.singleResult as Long).toInt()
          val data = dataQuery.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize).resultList;
          return CountResult(count, data)
      }*/
    public static CountResult  countDataFileds(String fileds, String headerSql, String tailSql, Map<String, Object> params
            , int pageIndex, int pageSize, EntityManager entityManager) {
        SqlAndParams sqlAndParams = sql(headerSql, params);
        String sql = (fileds != null && fileds.trim() != "") ? "select "+fileds + sqlAndParams.sql : sqlAndParams.sql;
        ;
        Query countQuery = entityManager.createQuery(countSql(sqlAndParams.sql));
        Query dataQuery = entityManager.createQuery(sql + " " + tailSql);
        for (int i = 0; i < sqlAndParams.params.size(); i++) {
            countQuery.setParameter(i, sqlAndParams.params.get(i));
            dataQuery.setParameter(i, sqlAndParams.params.get(i));
        }

        int count = ((Long) countQuery.getSingleResult()).intValue();
        List data = dataQuery.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize).getResultList();
        return new CountResult(count, data);
    }

    private static String countSql(String sql) {
        return "select count(0) " + sql;
    }

    private static SqlAndParams sql(String tableName, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(tableName).append(" where ");
        List paramsList = new ArrayList();

        params.forEach((k, v) -> {
            if (v != null) {
                if (k.trim().endsWith("?")) {
                    sql.append(k).append(" and ");
                }else if(k.trim().endsWith("? or")){
                    sql.append(k).append(" ");
                } else {
                    sql.append(k).append("=? and ");
                }
                paramsList.add(v);
            }
        });
        String finalSql = sql.toString().trim();
        if (finalSql.endsWith(" where")) {
            return new SqlAndParams(sql.toString().substring(0, finalSql.length() - 5), paramsList);
        }

        if (finalSql.endsWith("and")) {
            return new SqlAndParams(sql.toString().substring(0, finalSql.length() - 3), paramsList);
        }

        return new SqlAndParams(finalSql, paramsList);
    }

    public static Map<String,Object> mapOf(Object... args){
        Map map = new HashMap<>();
        for(int i=0;i<args.length;i+=2){
            map.put(args[i],args[i+1]);
        }
        return map;
    }

    private  static class SqlAndParams {
        public SqlAndParams(String sql, List params) {
            this.sql = sql;
            this.params = params;
        }

        String sql;
        List params;
    }


    /*fun countData(tableName: String, params: Map<String, *>, pageIndex: Int, pageSize: Int, entityManager: EntityManager): CountResult {
        return countDataFileds(null, tableName, "", params, pageIndex, pageSize, entityManager)
    }


    private fun countSql(sql: String): String {
        return "select count(0) $sql"
    }

    private fun sql(tableName: String, params: Map<String, *>): Pair<String, List<Any>> {
        val sql = StringBuilder(tableName).append(" where ")
        val arrParams = arrayListOf<Any>()
        params.forEach { k, v ->
            if (v != null) {
                if(k.trim().endsWith("?")){
                    sql.append(k).append(" and ")
                }else{
                    sql.append(k).append("=? and ")
                }
                arrParams.add(v)
            }
        }
        val finalSql = sql.toString().trim()
        if (finalSql.endsWith("where")) {
            return Pair(sql.toString().substring(0, finalSql.length - 5), arrParams)
        }

        if (finalSql.endsWith("and")) {
            return Pair(sql.toString().substring(0, finalSql.length - 3), arrParams)
        }

        return Pair(finalSql, arrParams)
    }*/
}
