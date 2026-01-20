package com.renderbase.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.renderbase.exceptions.RenderbaseException;
import com.renderbase.models.GenerateRequest;
import com.renderbase.models.GenerateResult;
import com.renderbase.models.ListResponse;
import com.renderbase.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource for document generation operations.
 *
 * <p>Use this resource to generate PDF and Excel documents from templates.</p>
 *
 * <h2>Example:</h2>
 * <pre>{@code
 * GenerateResult result = client.documents().generate(
 *     GenerateRequest.builder()
 *         .templateId("tmpl_invoice")
 *         .format("pdf")
 *         .variable("invoiceNumber", "INV-001")
 *         .variable("customerName", "Acme Corp")
 *         .workspaceId("ws_abc123")  // Optional
 *         .build()
 * );
 *
 * System.out.println("Download URL: " + result.getDownloadUrl());
 * }</pre>
 *
 * @since 1.0.0
 */
public class DocumentsResource {

    private final HttpClient httpClient;

    public DocumentsResource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Generates a document from a template.
     *
     * @param request The generation request containing template ID, format, variables, and optional workspaceId
     * @return The generation result with download URL
     * @throws RenderbaseException if the request fails
     */
    public GenerateResult generate(GenerateRequest request) throws RenderbaseException {
        return httpClient.post("/documents/generate", request, GenerateResult.class);
    }

    /**
     * Gets a document generation job by ID.
     *
     * @param jobId The job ID
     * @return The generation result
     * @throws RenderbaseException if the request fails
     */
    public GenerateResult get(String jobId) throws RenderbaseException {
        return httpClient.get("/documents/jobs/" + jobId, GenerateResult.class);
    }

    /**
     * Lists document generation jobs.
     *
     * @return Paginated list of generation results
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<GenerateResult> list() throws RenderbaseException {
        return list(null, null, null, null);
    }

    /**
     * Lists document generation jobs with pagination.
     *
     * @param page  Page number (1-based)
     * @param limit Number of items per page
     * @return Paginated list of generation results
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<GenerateResult> list(Integer page, Integer limit) throws RenderbaseException {
        return list(page, limit, null, null);
    }

    /**
     * Lists document generation jobs with pagination and filtering.
     *
     * @param page        Page number (1-based)
     * @param limit       Number of items per page
     * @param templateId  Filter by template ID
     * @param workspaceId Filter by workspace ID
     * @return Paginated list of generation results
     * @throws RenderbaseException if the request fails
     */
    public ListResponse<GenerateResult> list(Integer page, Integer limit, String templateId, String workspaceId) throws RenderbaseException {
        Map<String, String> params = new HashMap<>();
        if (page != null) {
            params.put("page", page.toString());
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }
        if (templateId != null) {
            params.put("templateId", templateId);
        }
        if (workspaceId != null) {
            params.put("workspaceId", workspaceId);
        }

        return httpClient.get("/documents/jobs", params, new TypeReference<ListResponse<GenerateResult>>() {});
    }

    /**
     * Deletes a generated document.
     *
     * @param jobId The job ID of the document to delete
     * @throws RenderbaseException if the request fails
     */
    public void delete(String jobId) throws RenderbaseException {
        httpClient.delete("/documents/jobs/" + jobId);
    }

    /**
     * Downloads a generated document as bytes.
     *
     * @param downloadUrl The download URL from the generation result
     * @return The document bytes
     * @throws RenderbaseException if the download fails
     */
    public byte[] download(String downloadUrl) throws RenderbaseException {
        try {
            java.net.URL url = new java.net.URL(downloadUrl);
            java.io.InputStream inputStream = url.openStream();
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            return outputStream.toByteArray();
        } catch (java.io.IOException e) {
            throw new RenderbaseException("Failed to download document", e);
        }
    }
}
