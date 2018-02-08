package istu.bacs.standings.db;

import istu.bacs.db.contest.Contest;

public interface ContestService {
    Contest findById(int contestId);
}