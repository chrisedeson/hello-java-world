package com.chris.helloworld;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HelloWorld {
    // Represents one focused study session for the module.
    private static class StudySession {
        String topic;
        double hours;
        String result;

        StudySession(String topic, double hours, String result) {
            this.topic = topic;
            this.hours = hours;
            this.result = result;
        }
    }

    // Entry point that builds data and prints a complete study report.
    public static void main(String[] args) {
        int targetHours = parseTargetHours(args);
        List<StudySession> sessions = buildStudySessions();

        // Write sessions to a file for record-keeping.
        saveSessionsToFile(sessions, "session_log.txt");

        printHeader("CSE 310 Java Module Progress Tracker");
        printSessionsTable(sessions);
        printSummary(sessions, targetHours);
        printActionItems(buildActionItems());

        // Read and display sessions from the saved file.
        System.out.println();
        printHeader("Loading Sessions from File");
        List<StudySession> loadedSessions = loadSessionsFromFile("session_log.txt");
        if (loadedSessions != null && !loadedSessions.isEmpty()) {
            System.out.println("Successfully loaded " + loadedSessions.size() + " sessions from session_log.txt");
            System.out.println();
        }
    }

    // Writes all study sessions to a file for persistent storage.
    private static void saveSessionsToFile(List<StudySession> sessions, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (StudySession session : sessions) {
                writer.write(session.topic + "|" + session.hours + "|" + session.result + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Reads study sessions from a file and reconstructs the list.
    private static List<StudySession> loadSessionsFromFile(String filename) {
        List<StudySession> sessions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String topic = parts[0];
                    double hours = Double.parseDouble(parts[1]);
                    String result = parts[2];
                    sessions.add(new StudySession(topic, hours, result));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return sessions;
    }

    // Parses an optional target hour argument and falls back to 20 if invalid.
    private static int parseTargetHours(String[] args) {
        int defaultTarget = 20;
        if (args.length == 0) {
            return defaultTarget;
        }

        try {
            int parsed = Integer.parseInt(args[0]);
            return parsed > 0 ? parsed : defaultTarget;
        } catch (NumberFormatException ex) {
            return defaultTarget;
        }
    }

    // Creates a realistic list of work sessions completed during the sprint.
    private static List<StudySession> buildStudySessions() {
        List<StudySession> sessions = new ArrayList<>();
        sessions.add(new StudySession("Java setup and project structure", 2.0, "Configured folders and packages"));
        sessions.add(new StudySession("Classes and methods", 2.5, "Built reusable methods"));
        sessions.add(new StudySession("Loops and conditionals", 2.0, "Implemented summary logic"));
        sessions.add(new StudySession("Collections (ArrayList)", 2.5, "Tracked session data in a list"));
        sessions.add(new StudySession("Formatting console output", 1.5, "Printed readable report tables"));
        sessions.add(new StudySession("Refactoring and comments", 2.0, "Improved naming and documentation"));
        sessions.add(new StudySession("README and submission prep", 2.0, "Prepared required artifacts"));
        return sessions;
    }

    // Prints a formatted title block for the report.
    private static void printHeader(String title) {
        String border = "=".repeat(60);
        System.out.println(border);
        System.out.println(title);
        System.out.println(border);
    }

    // Prints each study session in table format for quick review.
    private static void printSessionsTable(List<StudySession> sessions) {
        System.out.println("Session Log");
        System.out.printf("%-35s %-8s %s%n", "Topic", "Hours", "Result");
        System.out.println("-".repeat(75));

        for (StudySession session : sessions) {
            System.out.printf("%-35s %-8.1f %s%n", session.topic, session.hours, session.result);
        }

        System.out.println();
    }

    // Computes the total hours spent across all sessions.
    private static double calculateTotalHours(List<StudySession> sessions) {
        double total = 0.0;
        for (StudySession session : sessions) {
            total += session.hours;
        }
        return total;
    }

    // Computes average hours per session.
    private static double calculateAverageHours(List<StudySession> sessions) {
        if (sessions.isEmpty()) {
            return 0.0;
        }
        return calculateTotalHours(sessions) / sessions.size();
    }

    // Finds the session with the highest number of hours.
    private static StudySession findMostIntenseSession(List<StudySession> sessions) {
        if (sessions.isEmpty()) {
            return null;
        }

        StudySession best = sessions.get(0);
        for (StudySession session : sessions) {
            if (session.hours > best.hours) {
                best = session;
            }
        }
        return best;
    }

    // Finds the session with the lowest number of hours.
    private static StudySession findLightestSession(List<StudySession> sessions) {
        if (sessions.isEmpty()) {
            return null;
        }

        StudySession lightest = sessions.get(0);
        for (StudySession session : sessions) {
            if (session.hours < lightest.hours) {
                lightest = session;
            }
        }
        return lightest;
    }

    // Prints a concise progress summary against the weekly target.
    private static void printSummary(List<StudySession> sessions, int targetHours) {
        double totalHours = calculateTotalHours(sessions);
        double averageHours = calculateAverageHours(sessions);
        StudySession mostIntense = findMostIntenseSession(sessions);
        StudySession lightest = findLightestSession(sessions);
        double remaining = Math.max(0, targetHours - totalHours);

        System.out.println("Progress Summary");
        System.out.println("-".repeat(75));
        System.out.printf("Target hours: %d%n", targetHours);
        System.out.printf("Total hours completed: %.1f%n", totalHours);
        System.out.printf("Average hours/session: %.2f%n", averageHours);

        if (mostIntense != null) {
            System.out.printf("Most intense session: %s (%.1f hours)%n", mostIntense.topic, mostIntense.hours);
        }

        if (lightest != null) {
            System.out.printf("Lightest session: %s (%.1f hours)%n", lightest.topic, lightest.hours);
        }

        if (remaining == 0) {
            System.out.println("Status: Weekly target met or exceeded.");
        } else {
            System.out.printf("Status: %.1f more hours needed to hit target.%n", remaining);
        }

        System.out.println();
    }

    // Builds a practical checklist of deliverables for conference paper submission.
    private static List<String> buildActionItems() {
        List<String> actions = new ArrayList<>();
        actions.add("Verify all references are formatted according to conference style guide.");
        actions.add("Run plagiarism check and ensure originality score is acceptable.");
        actions.add("Have co-authors review and approve the final manuscript.");
        actions.add("Submit paper before deadline through conference management system.");
        actions.add("Prepare presentation slides based on paper content.");
        return actions;
    }

    // Prints action items with index numbers so progress is easy to track.
    private static void printActionItems(List<String> actions) {
        System.out.println("Submission Action Items");
        System.out.println("-".repeat(75));

        for (int i = 0; i < actions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, actions.get(i));
        }

        System.out.println("=".repeat(60));
    }
}

