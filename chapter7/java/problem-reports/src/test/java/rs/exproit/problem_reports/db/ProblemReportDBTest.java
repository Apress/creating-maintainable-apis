package rs.exproit.problem_reports.db;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import rs.exproit.problem_reports.resource.ProblemReport;
import static rs.exproit.problem_reports.db.ProblemReportDB.*;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProblemReportDBTest {
    @Test
    public final void findAConcreteProblemReportByTheReportNumber() {
        ProblemReport report = findProblemReport("1");
        assertNotNull(report);
        assertEquals("http://example.org/errors/out-of-memory", report.getType());
    }

    @Test
    public final void tryToFindANonexistentProblemReport() {
        ProblemReport report = findProblemReport("XYZ");
        assertNull(report);
    }
    
    @Test
    public final void getAllProblemReportsFromTheDatabase() {
        List<ProblemReport> reports = getAllProblemReports();
        assertNotNull(reports);
        assertFalse(reports.isEmpty());
        assertEquals(5, reports.size());
    }
    
    @Test
    public final void findAllProblemReportsWithTheGivenCorrelationId() {
        List<ProblemReport> reports = findAllProblemReports("T2");
        assertNotNull(reports);
        assertFalse(reports.isEmpty());
        assertEquals(2, reports.size());

        reports = findAllProblemReports("T1");
        assertNotNull(reports);
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());

        reports = findAllProblemReports("TXYZ");
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
    }
    
    @Test
    public final void removeAConcreteProblemReportByTheReportNumber() {
        assertNotNull(findProblemReport("1"));        
        deleteProblemReport("1");
        assertNull(findProblemReport("1"));
    }    
}