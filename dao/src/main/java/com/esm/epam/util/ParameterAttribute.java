package com.esm.epam.util;

import com.esm.epam.entity.Tag;

import java.util.List;

public class ParameterAttribute {
    public final static String DATABASE_DRIVER = "org.postgresql.Driver",
            DATABASE_URL = "jdbc:postgresql://localhost:5432/lab_module_2_projectDB",
            DATABASE_USERNAME = "postgres",
            DATABASE_PASSWORD = "root",

            TAG_TABLE = "tags",
            TAG = "tag",
            TAG_ID = "tag_id",
            TAG_NAME = "tag_name",

            CERTIFICATE_TABLE = "gift_certificates",
            CERTIFICATE_ID = "id",
            CERTIFICATE_NAME = "name",
            CERTIFICATE_DESCRIPTION = "description",
            CERTIFICATE_PRICE = "price",
            CERTIFICATE_DURATION = "duration",
            CERTIFICATE_CREATE_DATE = "creation_date",
            CERTIFICATE_LAST_UPDATE_DATE = "last_update_date",
            CERTIFICATE_LAST_UPDATE_DATE_FIELD = "lastUpdateDate",

            CERTIFICATES_TAGS_TABLE = "certificates_tags",
            CERTIFICATE_TAGS_CERTIFICATE_ID = "certificate_id",
            CERTIFICATE_TAGS_TAG_ID = "tag_id",

            BEGIN_UPDATE_QUERY = "UPDATE gift_certificates SET ",
            WHERE_UPDATE_QUERY = " WHERE gift_certificates.id = ",

            GET_ALL_CERTIFICATES_QUERY = "SELECT * FROM gift_certificates \n" +
            "LEFT JOIN certificates_tags ON (gift_certificates.id=certificates_tags.certificate_id) \n" +
            "LEFT JOIN tags ON (tags.tag_id = certificates_tags.tag_id)\n" +
            "ORDER BY gift_certificates.id",
            GET_CERTIFICATE_BY_ID_QUERY = "SELECT * FROM gift_certificates \n" +
                    "LEFT JOIN certificates_tags ON (gift_certificates.id=certificates_tags.certificate_id) \n" +
                    "LEFT JOIN tags ON(tags.tag_id = certificates_tags.tag_id)\n" +
                    "WHERE gift_certificates.id = ?\n",
            DELETE_CERTIFICATE_BY_ID_CERTIFICATES_TAGS_QUERY = "DELETE FROM certificates_tags\n" +
                    "    WHERE certificates_tags.certificate_id = ?;\n",
            DELETE_CERTIFICATE_BY_ID_QUERY =
                    "    DELETE FROM gift_certificates\n" +
                    "    WHERE gift_certificates.id = ?;",
            ADD_CERTIFICATE_QUERY = "INSERT INTO gift_certificates(name, description, duration, creation_date, price) \n" +
                    "VALUES (?, ?, ?, ?, ?)",
            ADD_CERTIFICATE_TAG_QUERY = "INSERT INTO certificates_tags(certificate_id, tag_id) VALUES (?,?)",
            GET_TAG_BY_NAME_QUERY = "SELECT * FROM tags WHERE tags.tag_name = ?",

            GET_ALL_TAGS_QUERY = "SELECT * FROM tags",
            GET_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE tags.tag_id = ?",
            ADD_TAG_QUERY = "INSERT INTO tags(tag_name) VALUES (?)",
            DELETE_TAG_BY_ID_QUERY = " DELETE FROM tags WHERE tags.tag_id = ?",
            DELETE_TAG_BY_ID_CERTIFICATES_TAG_QUERY = "DELETE FROM certificates_tags WHERE certificates_tags.tag_id = ?;",

            BEGIN_GET_FILTERED_CERTIFICATE_LIST_QUERY = "SELECT * FROM gift_certificates \n" +
            "LEFT JOIN certificates_tags ON (gift_certificates.id=certificates_tags.certificate_id) \n" +
            "LEFT JOIN tags ON (tags.tag_id = certificates_tags.tag_id)",
            ORDER_BY_STATEMENT = " ORDER BY ",

            NAME_ASC_PARAMETER = "name_asc",
            NAME_DESC_PARAMETER = "name_desc",
            DATE_ASC_PARAMETER = "date_asc",
            DATE_DESC_PARAMETER = "date_desc",

            SORT_STATEMENT = "sort",
            WHERE_STATEMENT = " WHERE ",
            FROM_STATEMENT = " FROM ",
            SELECT_STATEMENT = " SELECT ",
            AND_STATEMENT = " AND ",
            LIKE_STATEMENT = " LIKE ",
            OR_STATEMENT = " OR ",
            IN_STATEMENT = " IN ",
            ASC_STATEMENT = "ASC",
            DESC_STATEMENT = "DESC";
}
