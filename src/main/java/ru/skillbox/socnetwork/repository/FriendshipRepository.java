package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Friendship;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;
import ru.skillbox.socnetwork.model.mapper.FriendshipMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FriendshipRepository {

    private final JdbcTemplate jdbc;

    public void removeFriendlyStatusByPersonIds(Integer srcPersonId, Integer dstPersonId) {
        jdbc.update("" +
                "DELETE FROM friendship f\n" +
                "WHERE f.code = 'FRIEND' and f.src_person_id = ? and f. dst_person_id = ?", srcPersonId, dstPersonId);
    }

    public void createFriendRequestByPersonIds(Integer srcPersonId, Integer dstPersonId) {
        jdbc.update("" +
                "INSERT INTO friendship (src_person_id, dst_person_id, time, code) " +
                "VALUES (?, ?, to_timestamp(?), CAST(? AS code_type))",
                srcPersonId, dstPersonId, System.currentTimeMillis(), TypeCode.REQUEST.toString());
    }

    public Optional<Friendship> getFriendlyStatusByPersonIdsAndCode(Integer srcPersonId, Integer dstPersonId, String typeCode) {
        return jdbc.query("" +
                "SELECT *\n" +
                "FROM friendship f\n" +
                "WHERE f.code = CAST(? AS code_type) AND f.src_person_id = ? AND f.dst_person_id = ?", new FriendshipMapper(),
                typeCode, srcPersonId, dstPersonId)
                .stream()
                .findAny();
    }

    public void updateFriendlyStatusByPersonIdsAndCode(Integer srcPersonId, Integer dstPersonId, String typeCode) {
        jdbc.update("" +
                "UPDATE friendship SET time = to_timestamp(?), code = CAST(? AS code_type) " +
                "WHERE src_person_id = ? and dst_person_id = ?",
                System.currentTimeMillis(), typeCode, srcPersonId, dstPersonId);
    }

}
