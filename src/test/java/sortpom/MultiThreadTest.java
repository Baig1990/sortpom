package sortpom;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertEquals;

public class MultiThreadTest {
    private AtomicInteger counter = new AtomicInteger(1);

    @Test
    public final void multipleSortingsShouldNotInterfereWithEachOther() throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        for (int a = 0; a < 10; a++) {
            testOneConcurrentLoop(executorService);
        }
    }

    private void testOneConcurrentLoop(final ExecutorService executorService) throws InterruptedException, ExecutionException {
        List<Callable<Boolean>> testThreads = getTestThreads();
        List<Future<Boolean>> futures = executorService.invokeAll(testThreads);
        assertAllThreadsReturnedTrue(futures);
    }

    private List<Callable<Boolean>> getTestThreads() {
        List<Callable<Boolean>> list = new ArrayList<Callable<Boolean>>();
        list.add(new TestThread("/full_unsorted_input.xml",
                "/sortOrderFiles/sorted_default_0_4_0.xml", "default_0_4_0"));
        list.add(new TestThread("/full_unsorted_input.xml",
                "/sortOrderFiles/sorted_custom_1.xml", "custom_1"));
        list.add(new TestThread("/full_unsorted_input.xml",
                "/sortOrderFiles/sorted_recommended_2008_06.xml", "recommended_2008_06"));
        list.add(new TestThread("/full_unsorted_input.xml",
                "/sortOrderFiles/sorted_default_1_0_0.xml", "default_1_0_0"));
        return list;
    }

    private void assertAllThreadsReturnedTrue(final List<Future<Boolean>> futures) throws InterruptedException, ExecutionException {
        for (Future<Boolean> future : futures) {
            assertEquals(true, future.get());
        }
    }

    private class TestThread implements Callable<Boolean> {
        private final String inputResourceFileName;
        private final String expectedResourceFileName;
        private final String predefinedSortOrder;

        public TestThread(final String inputResourceFileName,
                          final String expectedResourceFileName,
                          final String predefinedSortOrder) {
            this.inputResourceFileName = inputResourceFileName;
            this.expectedResourceFileName = expectedResourceFileName;
            this.predefinedSortOrder = predefinedSortOrder;
        }

        @Override
        public Boolean call() {
            try {
                SortOrderFilesUtil.testFilesWithUniqueFilenames(inputResourceFileName,
                        expectedResourceFileName,
                        predefinedSortOrder, counter.getAndIncrement());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
