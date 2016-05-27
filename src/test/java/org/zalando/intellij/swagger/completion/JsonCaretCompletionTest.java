package org.zalando.intellij.swagger.completion;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

import java.util.List;
import java.util.stream.Collectors;

public class JsonCaretCompletionTest {

    private SwaggerFixture myFixture;

    @Before
    public void setUpBefore() throws Exception {
        myFixture = SwaggerFixture.forResourceFolder("testing/completion/json");
    }

    @After
    public void tearDownAfter() throws Exception {
        myFixture.tearDown();
    }

    @Test
    public void thatJsonKeyInsideQuotesIsCompleted() {
        getCaretCompletions("key_inside_quotes")
                .assertContains(getExpectedElements())
                .assertNotContains(getExistingElements());
    }

    @Test
    public void thatJsonKeyWithNoQuotesIsCompleted() {
        final List<String> keysInQuotes = getExpectedElements().stream()
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.toList());

        getCaretCompletions("key_with_no_quotes")
                .assertContains(keysInQuotes)
                .assertNotContains(getExistingElements());
    }

    @Test
    public void thatJsonKeyWithStartingQuoteIsCompleted() {
        getCaretCompletions("key_with_starting_quote")
                .assertContains(getExpectedElements())
                .assertNotContains(getExistingElements());
    }

    private List<String> getExistingElements() {
        return Lists.newArrayList("swagger", "host");
    }

    private List<String> getExpectedElements() {
        return Lists.newArrayList("basePath", "produces", "consumes", "schemes",
                "paths", "tags", "parameters", "responses");
    }

    @NotNull
    private SwaggerFixture.AssertableList getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, JsonOrYaml.JSON);
    }

}