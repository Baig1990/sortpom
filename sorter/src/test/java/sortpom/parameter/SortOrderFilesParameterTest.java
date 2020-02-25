package sortpom.parameter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import sortpom.exception.FailureException;
import sortpom.util.SortPomImplUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SortOrderFilesParameterTest {

    @Test
    public final void incorrectCustomSortOrderShouldThrowException() {

        final Executable testMethod = () -> SortPomImplUtil.create()
                .defaultOrderFileName("difforder/VERYdifferentOrder.xml")
                .testFiles("/full_unsorted_input.xml", "/sortOrderFiles/sorted_differentOrder.xml");

        final FailureException thrown = assertThrows(FailureException.class, testMethod);

        assertThat(thrown.getMessage(), endsWith("VERYdifferentOrder.xml in classpath"));
    }

    @Test
    public final void incorrectPredefinedSortOrderShouldThrowException() {

        final Executable testMethod = () -> SortPomImplUtil.create()
                .predefinedSortOrder("abbie_normal_brain")
                .lineSeparator("\n")
                .testFiles("/full_unsorted_input.xml",
                        "/sortOrderFiles/sorted_default_0_4_0.xml");

        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, testMethod);

        assertThat(thrown.getMessage(), is(equalTo("Cannot find abbie_normal_brain.xml among the predefined plugin resources")));
    }

    @Test
    public final void incorrectCustomSortOrderShouldThrowException2() {

        final Executable testMethod = () -> SortPomImplUtil.create()
                .defaultOrderFileName("difforder/VERYdifferentOrder.xml")
                .testVerifyXmlIsOrdered("/sortOrderFiles/sorted_differentOrder.xml");

        final FailureException thrown = assertThrows(FailureException.class, testMethod);

        assertThat(thrown.getMessage(), endsWith("VERYdifferentOrder.xml in classpath"));
    }

    @Test
    public final void incorrectPredefinedSortOrderShouldThrowException2() {

        final Executable testMethod = () ->SortPomImplUtil.create()
                .predefinedSortOrder("abbie_normal_brain")
                .lineSeparator("\n")
                .testVerifyXmlIsOrdered("/sortOrderFiles/sorted_default_0_4_0.xml");

        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, testMethod);

        assertThat(thrown.getMessage(), is(equalTo("Cannot find abbie_normal_brain.xml among the predefined plugin resources")));
    }

}
