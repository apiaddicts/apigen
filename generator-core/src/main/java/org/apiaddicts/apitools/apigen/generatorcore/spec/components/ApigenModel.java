package org.apiaddicts.apitools.apigen.generatorcore.spec.components;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Data
public class ApigenModel {
    private boolean readOnly = false;
    private ModelRelationalPersistence relationalPersistence = new ModelRelationalPersistence();
    private List<ApigenModelAttribute> attributes = new ArrayList<>();

    @Data
    public static class ModelRelationalPersistence {
        private String table;

    }

    @Data
    public static class ApigenModelAttribute {
        private String name;
        private String type;
        private String itemsType;
        private AttributeRelationalPersistence relationalPersistence = new AttributeRelationalPersistence();
        private List<AttributeValidation> validations = new ArrayList<>();
        private List<ApigenModelAttribute> attributes = new ArrayList<>();
    }

    @Data
    public static class AttributeRelationalPersistence {
        private String column;
        private SortedMap<String, String> columns;
        private boolean primaryKey = false;
        private boolean unique = false;
        private boolean autogenerated = false;
        private String foreignColumn;
        private SortedMap<String, String> foreignColumns;
        private String intermediateTable;
        private boolean owner;
        private String sequence;
    }

    @Data
    public static class AttributeValidation {
        private String type;
        private Integer min;
        private Integer max;
        private String regex;
        private String value;
        private Integer integer;
        private Integer fraction;
        private boolean inclusive;
    }
}
