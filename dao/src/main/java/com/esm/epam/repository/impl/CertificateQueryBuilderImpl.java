package com.esm.epam.repository.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.repository.QueryBuilder;
import com.esm.epam.entity.Certificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.esm.epam.util.ParameterAttribute.*;

@Component
@ComponentScan(basePackages = {"com.esm.epam.repository.impl"})
@Qualifier(value = "certificateQueryBuilder")
public class CertificateQueryBuilderImpl implements QueryBuilder<Certificate> {

    @Override
    public String getUpdateQuery(Certificate certificate, Long idCertificate) {
        String query = BEGIN_UPDATE_QUERY;
        Map<String, Object> fieldsToBeUpdated = getFieldsToBeUpdated(certificate);
        String values = fieldsToBeUpdated.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(", "));

        query = query + values + WHERE_UPDATE_QUERY + idCertificate;
        return query;
    }

    @Override
    public String getFilteredList(MultiValueMap<String, Object> params) {
        String query = BEGIN_GET_FILTERED_CERTIFICATE_LIST_QUERY;
        String whereQuery = "";
        String orderByQuery = "";
        for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case TAG:
                    whereQuery = getStatementByTagFilter(entry, whereQuery);
                    break;
                case CERTIFICATE_NAME:
                    whereQuery = getWhereStatementByValue(whereQuery, entry, CERTIFICATE_NAME);
                    break;
                case CERTIFICATE_DESCRIPTION:
                    whereQuery = getWhereStatementByValue(whereQuery, entry, CERTIFICATE_DESCRIPTION);
                    break;
                case SORT_STATEMENT:
                    orderByQuery = getOrderByStatement(orderByQuery, entry);
                    break;
            }
        }
        if (orderByQuery.length() == 0) {
            orderByQuery = orderByQuery + ORDER_BY_STATEMENT + CERTIFICATE_TABLE + "." + CERTIFICATE_ID;
        }

        query = query + whereQuery + orderByQuery;
        return query;
    }

    private String getOrderByStatement(String orderByQuery, Map.Entry<String, List<Object>> entry) {
        for (int i = 0; i < entry.getValue().size(); i++) {
            switch (entry.getValue().get(i).toString()) {
                case NAME_ASC_PARAMETER:
                    orderByQuery = getOrderByStatementByValue(orderByQuery, entry, CERTIFICATE_NAME, ASC_STATEMENT);
                    return orderByQuery;
                case NAME_DESC_PARAMETER:
                    orderByQuery = getOrderByStatementByValue(orderByQuery, entry, CERTIFICATE_NAME, DESC_STATEMENT);
                    break;
                case DATE_ASC_PARAMETER:
                    orderByQuery = getOrderByStatementByValue(orderByQuery, entry, CERTIFICATE_CREATE_DATE, ASC_STATEMENT);
                    break;
                case DATE_DESC_PARAMETER:
                    orderByQuery = getOrderByStatementByValue(orderByQuery, entry, CERTIFICATE_CREATE_DATE, DESC_STATEMENT);
                    break;
            }
            if (i != entry.getValue().size() - 1) {
                orderByQuery = orderByQuery + " , ";
            }
        }
        return orderByQuery;
    }

    private String getOrderByStatementByValue(String orderByQuery, Map.Entry<String, List<Object>> entry, String value, String direction) {
        orderByQuery = prepareOrderStatement(orderByQuery);
        orderByQuery = orderByQuery + CERTIFICATE_TABLE + "." + value + " " + direction;
        return orderByQuery;
    }

    private String getWhereStatementByValue(String whereQuery, Map.Entry<String, List<Object>> entry, String value) {
        whereQuery = prepareWhereStatement(whereQuery);
        whereQuery = whereQuery + " ( ";
        for (int i = 0; i < entry.getValue().size(); i++) {
            whereQuery = whereQuery + CERTIFICATE_TABLE + "." + value + LIKE_STATEMENT + "\'%" + entry.getValue().get(i) + "%\'";
            if (i != entry.getValue().size() - 1) {
                whereQuery = whereQuery + OR_STATEMENT;
            }
        }
        whereQuery = whereQuery + " ) ";

        return whereQuery;
    }

    private String getStatementByTagFilter(Map.Entry<String, List<Object>> entry, String whereStatement) {

        whereStatement = prepareWhereStatement(whereStatement);
        whereStatement = whereStatement + " ( " + CERTIFICATE_TABLE + "." + CERTIFICATE_ID + IN_STATEMENT + "(" + SELECT_STATEMENT +
                CERTIFICATES_TAGS_TABLE + "." + CERTIFICATE_TAGS_CERTIFICATE_ID + FROM_STATEMENT + CERTIFICATES_TAGS_TABLE + WHERE_STATEMENT + CERTIFICATES_TAGS_TABLE + "." + CERTIFICATE_TAGS_TAG_ID + " = " + entry.getValue().get(0) + " )) ";
        return whereStatement;
    }

    private String prepareWhereStatement(String whereQuery) {
        if (whereQuery.length() == 0) {
            whereQuery = WHERE_STATEMENT;
        } else {
            whereQuery = whereQuery + AND_STATEMENT;
        }
        return whereQuery;
    }

    private String prepareOrderStatement(String orderByStatement) {
        if (orderByStatement.length() == 0) {
            orderByStatement = ORDER_BY_STATEMENT;
        } else {
            orderByStatement = orderByStatement + " , ";
        }
        return orderByStatement;
    }

    private Map<String, Object> getFieldsToBeUpdated(Certificate certificate) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapFields = mapper.convertValue(certificate, Map.class);

        mapFields.values().removeIf(Objects::isNull);
        mapFields.values().removeIf(values -> values instanceof List);
        if (mapFields.keySet().contains(CERTIFICATE_LAST_UPDATE_DATE_FIELD)) {
            mapFields.put(CERTIFICATE_LAST_UPDATE_DATE, mapFields.remove(CERTIFICATE_LAST_UPDATE_DATE_FIELD));
        }
        Map<String, Object> result = mapFields.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> getStringWithQuotes(e.getValue())));
        return result;
    }

    private Object getStringWithQuotes(Object object) {
        if (object.getClass() == String.class) {
            String tmpValue = (String) object;
            object = "\'" + tmpValue + "\'";
        }
        return object;
    }

}
