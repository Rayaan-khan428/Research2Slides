package pdfextraction;

import java.util.*;
import org.asynchttpclient.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TextSummarizer {

    /**
     * summarize every paragraph for every slide in the arraylist and overwrite the non summarized paragraph
     * @param presentation
     * @throws InterruptedException
     */
    public static void summarize(ArrayList<Slide> presentation) throws InterruptedException {

        // Create a Gson object
        Gson gson = new Gson();

        for (int i = 0; i <  presentation.size(); i++) {

            Response response = null;
            JsonObject jsonObject = null;
            try {

                AsyncHttpClient client = new DefaultAsyncHttpClient();
                String apiKey = "q88H9vLvPMgp25ouFSDrRSb3vLL8zwRH9y3jXBXr";

                String text = presentation.get(i).getParagraph();
                String summary = "";

                String regex = "\\*{3}(?:END|START) OF PAGE \\d+\\*{3}";
                String quotations = "(?<!\\\\)\"";

                String preSummarization = text.replaceAll(regex, "");
                preSummarization = preSummarization.replaceAll("\\s+", " ").trim();
                preSummarization = preSummarization.replaceAll(quotations, "\\\\\"");

                presentation.get(i).setParagraph(preSummarization); // if less than 250 chars we still have something to put

                String requestBody = String.format("{\"text\":\"%s\", \"length\":\"short\", \"format\":\"bullets\", \"model\":\"summarize-xlarge\"}", preSummarization);

                Request request = client.prepare("POST", "https://api.cohere.ai/v1/summarize")
                        .setHeader("accept", "application/json")
                        .setHeader("content-type", "application/json")
                        .setHeader("authorization", "Bearer " + apiKey)
                        .setBody(requestBody)
                        .build();

                // send the request and wait for response
                response = client.executeRequest(request).get();

                // Parse the JSON string to a JsonObject
                jsonObject = gson.fromJson(response.getResponseBody(), JsonObject.class);

                if (jsonObject.has("summary") && !jsonObject.get("summary").isJsonNull()) {
                    // Extract the "summary" attribute
                    summary = jsonObject.get("summary").getAsString();

                } else {
                    // Handle the case where "summary" attribute is missing or null
                    summary = preSummarization;
                }

                // update presentation paragraph to bullet points
                summary = summary.replaceAll("-", "");
                presentation.get(i).setParagraph(summary);

                System.out.println("-----------------------Text Being Summarized----------------------");
                System.out.println("\nRaw Json for Debugging: ");
                System.out.println(response.getResponseBody());
                System.out.println("\nPreSummarization: ");
                System.out.println(preSummarization);
                System.out.println("\nAfterSummarization: ");
                System.out.println(summary);
                System.out.println("\n");

                client.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

