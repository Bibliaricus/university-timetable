package ua.com.foxminded.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

import org.springframework.jdbc.core.RowMapper;

import ua.com.foxminded.domain.Lesson;

public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("lesson_id"));
        lesson.setName(rs.getString("lesson_name"));        
        lesson.setAudience(rs.getString("audience"));              
        lesson.setDay(DayOfWeek.of(rs.getInt("week_day")));        
        return lesson;
    }

}
