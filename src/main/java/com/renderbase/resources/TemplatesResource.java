package com.renderbase.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.renderbase.exceptions.RenderbaseException;
import com.renderbase.models.ListResponse;
import com.renderbase.models.Template;
import com.renderbase.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource for template operations.
 *
 * <p>Use this resource to list and retrieve templates.</p>
 *
 * <h2>Example:</h2>
 * <pre>{@code
 * // List all templates
 * ListResponse<Template> templates = client.templates().list();
 * for (Template template : templates.getData()) {
 *     System.out.println(template.getName() + ": " + template.getId());
 * }
 *
 * // Get a specific template
 * Template template = client.templates().get("tmpl_invoice");
 * System.out.println("Variables: " + template.getVariables().size());
 * }</pre>
 *
 * @since 1.0.0
 */
public class TemplatesResource {

    private final HttpClient httpClient;

    public TemplatesResource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Lists all templates.
     *
     * @return Paginated list of templates
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<Template> list() throws RenderbaseException {
        return list(null, null, null);
    }

    /**
     * Lists templates with pagination.
     *
     * @param page  Page number (1-based)
     * @param limit Number of items per page
     * @return Paginated list of templates
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<Template> list(Integer page, Integer limit) throws RenderbaseException {
        return list(page, limit, null);
    }

    /**
     * Lists templates with pagination and filtering.
     *
     * @param page  Page number (1-based)
     * @param limit Number of items per page
     * @param type  Filter by template type (e.g., "pdf", "excel")
     * @return Paginated list of templates
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<Template> list(Integer page, Integer limit, String type) throws RenderbaseException {
        Map<String, String> params = new HashMap<>();
        if (page != null) {
            params.put("page", page.toString());
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }
        if (type != null) {
            params.put("type", type);
        }

        return httpClient.get("/templates", params, new TypeReference<ListResponse<Template>>() {});
    }

    /**
     * Gets a template by ID.
     *
     * @param templateId The template ID (UUID, shortId, or slug)
     * @return The template
     * @throws RenderbaseException if the request fails
     */
    public Template get(String templateId) throws RenderbaseException {
        return httpClient.get("/templates/" + templateId, Template.class);
    }

    /**
     * Gets a template by short ID.
     *
     * @param shortId The template short ID (e.g., "tmpl_abc123")
     * @return The template
     * @throws RenderbaseException if the request fails
     */
    public Template getByShortId(String shortId) throws RenderbaseException {
        return get(shortId);
    }

    /**
     * Gets a template by slug.
     *
     * @param slug The template slug (e.g., "invoice-template")
     * @return The template
     * @throws RenderbaseException if the request fails
     */
    public Template getBySlug(String slug) throws RenderbaseException {
        return get(slug);
    }
}
