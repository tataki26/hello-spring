package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 회원 저장: MemberRepository
// 실제 구현: 각 구현체(메모리/jdbc...)
public class JdbcMemberRepository implements MemberRepository {

    // db에 붙기 위해 필요한 변수
    private final DataSource dataSource;

    // spring으로부터 주입 받음
    // properties에서 셋팅을 하면 DataSource 생성(db 접속 정보)
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        // db connection 얻기 (socket)
        // dataSource.getConnection();
    }

    @Override
    public Member save(Member member) {

        /*
        // ? 파라미터 바인딩
        String sql = "insert into member(name) values(?)";

        Connection conn = dataSource.getConnection();

        // 문장 작성, sql 쿼리
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // member로부터 name 가져오기
        pstmt.setString(1, member.getName());

        // db에 쿼리 실행
        pstmt.executeUpdate();
         */

        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        // 결과를 받기 위한 변수
        ResultSet rs = null;

        try {

            conn = getConnection();
            // 자동 생성 키 사용
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // ? 안에 들어가는 인자
            pstmt.setString(1, member.getName());
            // db에 실제 쿼리 적용
            pstmt.executeUpdate();
            // key 반환
            rs = pstmt.getGeneratedKeys();

            // 값을 가지고 있으면
            if (rs.next()) {
                // 값을 가져와 member에 셋팅
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }

            return member;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 자원을 다 쓰고 나면 반드시 반환할 것
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findById(Long id) {

        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            // 조회
            rs = pstmt.executeQuery();

            if(rs.next()) {
                // 값이 있으면 member에 담아서 return
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);

            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public List<Member> findAll() {

        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            // 루프 돌면서 리스트에 추가
            while(rs.next()) {

                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);

            }

            return members;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {

        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if(rs.next()) {

                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);

            }

            // 없으면 empty 반환
            return Optional.empty();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    // DataSourceUtils를 통해 getConnection을 해야 트랜잭션 시에도 동일한 connection 유지 가능
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        // close도 DataSourceUtils를 통해서 해야 한다
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}