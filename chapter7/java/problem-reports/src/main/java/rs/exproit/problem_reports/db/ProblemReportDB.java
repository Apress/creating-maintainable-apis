package rs.exproit.problem_reports.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rs.exproit.problem_reports.resource.ProblemReport;

public final class ProblemReportDB {
    private static List<ProblemReport> database = new ArrayList<>();
    
    static {
        database.add(new ProblemReport("1", 
                "http://example.org/errors/out-of-memory", "Out of memory error", 500, "T1"));
        database.add(new ProblemReport("2", 
                "http://example.org/errors/authorization-error", "Invalid token", 401, "T2"));
        database.add(new ProblemReport("3", 
                "http://example.org/errors/resource-not-found", "Resource not found", 404, "T2"));
        database.add(new ProblemReport("4", 
                "http://example.org/errors/out-of-credit", "Out of credit", 403, "T3"));
        database.add(new ProblemReport("5", 
                "http://example.org/errors/invalid-request", "Invalid request", 400, "T4"));
    }
    
    public static ProblemReport findProblemReport(String reportNumber) {
        for (ProblemReport report : database) {
            if (report.getReportNumber().equals(reportNumber)) {
                return report;
            }
        }
        return null;
    }

    public static List<ProblemReport> getAllProblemReports() {
        return database;
    }
    
    public static List<ProblemReport> findAllProblemReports(String correlationId) {
        List<ProblemReport> result = new ArrayList<>();
        
        for (ProblemReport report : database) {
            if (report.getCorrelationId().equals(correlationId)) {
                result.add(report);
            }
        }
        return result;
    }

    public static void deleteProblemReport(String reportNumber) {
        Iterator<ProblemReport> it = database.iterator();
        while (it.hasNext()) {
            ProblemReport report = it.next();
            if (report.getReportNumber().equals(reportNumber)) {
                it.remove();
                return;
            }
        }
    }
}
