package sortpom.sort;

import org.junit.jupiter.api.Test;
import sortpom.util.SortPomImplUtil;

public class SortPropertiesTest {

    @Test
    public final void namedParametersInSortFileShouldSortThemFirst() throws Exception {
        SortPomImplUtil.create()
                .defaultOrderFileName("difforder/sortedPropertiesOrder.xml")
                .lineSeparator("\n")
                .testFiles("/SortedProperties_input.xml", "/SortedProperties_output.xml");
    }

    @Test
    public final void sortPropertyParameterShouldSortAlphabetically() throws Exception {
        SortPomImplUtil.create()
                .sortProperties()
                .lineSeparator("\n")
                .predefinedSortOrder("custom_1")
                .testFiles("/SortedProperties_input.xml", "/SortedProperties_output_alfa.xml");
    }

    @Test
    public final void testBothNamedParametersInSortFileAndSortPropertyParameterTest() throws Exception {
        SortPomImplUtil.create()
                .lineSeparator("\n")
                .defaultOrderFileName("difforder/sortedPropertiesOrder.xml")
                .sortProperties()
                .testFiles("/SortedProperties_input.xml", "/SortedProperties_output_alfa2.xml");
    }

    @Test
    public final void sortingOfFullPomFileShouldWork() throws Exception {
        SortPomImplUtil.create()
                .sortProperties()
                .sortPlugins("groupId,artifactId")
                .sortDependencies("groupId,artifactId")
                .lineSeparator("\n")
                .testFiles("/SortProp_input.xml", "/SortProp_expected.xml");
    }

    @Test
    public final void duplicatePropertiesShouldNotTriggerSorting() throws Exception {
        SortPomImplUtil.create()
                .sortProperties()
                .sortPlugins("groupId,artifactId")
                .sortDependencies("groupId,artifactId")
                .lineSeparator("\n")
                .testNoSorting("/SortProp_input_duplicate.xml");
    }
}
