package org.infoshere.model;

public enum TypeActivity {
        ENDURANCE("endurance"),
        STRENGTH("strength"),
        BALANCE("balance"),
        FLEXIBILITY("flexibility");

        public final String label;

        private TypeActivity(String label) {
                this.label = label;
        }

}
