package com.renderbase;

import com.renderbase.resources.DocumentsResource;
import com.renderbase.resources.TemplatesResource;
import com.renderbase.resources.WebhooksResource;
import com.renderbase.utils.HttpClient;

/**
 * Renderbase Java SDK client.
 *
 * <p>The main entry point for interacting with the Renderbase API.
 * Use this client to generate PDF and Excel documents from templates.</p>
 *
 * <h2>Example usage:</h2>
 * <pre>{@code
 * Renderbase client = new Renderbase("your-api-key");
 *
 * // Generate a PDF document
 * Map<String, Object> variables = new HashMap<>();
 * variables.put("invoiceNumber", "INV-001");
 * variables.put("customerName", "Acme Corp");
 *
 * GenerateResult result = client.documents().generate(
 *     GenerateRequest.builder()
 *         .templateId("tmpl_invoice")
 *         .format("pdf")
 *         .variables(variables)
 *         .build()
 * );
 *
 * System.out.println("Download URL: " + result.getDownloadUrl());
 * }</pre>
 *
 * @since 1.0.0
 */
public class Renderbase {

    private static final String DEFAULT_BASE_URL = "https://api.renderbase.dev/api/v1";

    private final HttpClient httpClient;
    private final DocumentsResource documents;
    private final TemplatesResource templates;
    private final WebhooksResource webhooks;

    /**
     * Creates a new Renderbase client with the specified API key.
     *
     * @param apiKey Your Renderbase API key
     */
    public Renderbase(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL);
    }

    /**
     * Creates a new Renderbase client with a custom base URL.
     *
     * @param apiKey  Your Renderbase API key
     * @param baseUrl Custom API base URL
     */
    public Renderbase(String apiKey, String baseUrl) {
        this(new RenderbaseConfig(apiKey, baseUrl));
    }

    /**
     * Creates a new Renderbase client with the specified configuration.
     *
     * @param config Client configuration
     */
    public Renderbase(RenderbaseConfig config) {
        if (config.getApiKey() == null || config.getApiKey().isEmpty()) {
            throw new IllegalArgumentException("API key is required");
        }

        this.httpClient = new HttpClient(config);
        this.documents = new DocumentsResource(httpClient);
        this.templates = new TemplatesResource(httpClient);
        this.webhooks = new WebhooksResource(httpClient);
    }

    /**
     * Returns the Documents resource for document generation operations.
     *
     * @return Documents resource
     */
    public DocumentsResource documents() {
        return documents;
    }

    /**
     * Returns the Templates resource for template operations.
     *
     * @return Templates resource
     */
    public TemplatesResource templates() {
        return templates;
    }

    /**
     * Returns the Webhooks resource for webhook operations.
     *
     * @return Webhooks resource
     */
    public WebhooksResource webhooks() {
        return webhooks;
    }

    /**
     * Creates a new builder for Renderbase configuration.
     *
     * @return Configuration builder
     */
    public static RenderbaseConfig.Builder builder() {
        return new RenderbaseConfig.Builder();
    }
}
