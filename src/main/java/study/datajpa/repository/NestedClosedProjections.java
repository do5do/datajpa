package study.datajpa.repository;

public interface NestedClosedProjections {
    String getUsername(); // 첫번째는 최적화가 된다.
    TeamInfo getTeam(); // 두번째부터는 최적화가 안된다. -> 팀의 이름만이 아니라 모든 컬럼을 조회

    interface TeamInfo {
        String getName();
    }
}
