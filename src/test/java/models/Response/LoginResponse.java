package models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    private String token;
    private Support support;

    @JsonProperty("_meta")
    private Meta meta;

    public LoginResponse() {
        // Default constructor for Jackson
    }

    public LoginResponse(String token, Support support, Meta meta) {
        this.token = token;
        this.support = support;
        this.meta = meta;
    }

    // 1. Getters and Setters for top-level fields
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Support getSupport() { return support; }
    public void setSupport(Support support) { this.support = support; }

    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    // 2. Inner class for the "support" JSON object
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Support {
        private String url;
        private String text;

        @JsonProperty("sponsored_by")
        private String sponsoredBy;

        public Support() {
            // Default constructor for Jackson
        }

        public Support(String url, String text, String sponsoredBy) {
            this.url = url;
            this.text = text;
            this.sponsoredBy = sponsoredBy;
        }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getSponsoredBy() { return sponsoredBy; }
        public void setSponsoredBy(String sponsoredBy) { this.sponsoredBy = sponsoredBy; }
    }

    // 3. Inner class for the "_meta" JSON object
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        @JsonProperty("powered_by")
        private String poweredBy;

        @JsonProperty("docs_url")
        private String docsUrl;

        @JsonProperty("upgrade_url")
        private String upgradeUrl;

        @JsonProperty("example_url")
        private String exampleUrl;

        private String variant;
        private String message;
        private Cta cta;

        public Meta() {
            // Default constructor for Jackson
        }

        public Meta(String poweredBy, String docsUrl, String upgradeUrl, String exampleUrl, String variant, String message, Cta cta) {
            this.poweredBy = poweredBy;
            this.docsUrl = docsUrl;
            this.upgradeUrl = upgradeUrl;
            this.exampleUrl = exampleUrl;
            this.variant = variant;
            this.message = message;
            this.cta = cta;
        }

        public String getPoweredBy() { return poweredBy; }
        public void setPoweredBy(String poweredBy) { this.poweredBy = poweredBy; }

        public String getDocsUrl() { return docsUrl; }
        public void setDocsUrl(String docsUrl) { this.docsUrl = docsUrl; }

        public String getUpgradeUrl() { return upgradeUrl; }
        public void setUpgradeUrl(String upgradeUrl) { this.upgradeUrl = upgradeUrl; }

        public String getExampleUrl() { return exampleUrl; }
        public void setExampleUrl(String exampleUrl) { this.exampleUrl = exampleUrl; }

        public String getVariant() { return variant; }
        public void setVariant(String variant) { this.variant = variant; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Cta getCta() { return cta; }
        public void setCta(Cta cta) { this.cta = cta; }
    }

    // 4. Inner class for the "cta" JSON object inside "_meta"
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cta {
        private String label;
        private String url;

        public Cta() {
            // Default constructor for Jackson
        }

        public Cta(String label, String url) {
            this.label = label;
            this.url = url;
        }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", support=" + support +
                ", meta=" + meta +
                '}';
    }

}
