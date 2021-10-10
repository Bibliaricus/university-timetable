package ua.com.foxminded.dao.aspects;

import java.time.DayOfWeek;
import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import ua.com.foxminded.dao.exceptions.DAOException;

@Aspect
@Configuration
public class LessonDAOAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonDAOAspect.class);

    @Pointcut("execution (java.util.List ua.com.foxminded.dao.LessonDAO.getGroupDayLessons(int, java.time.DayOfWeek))")
    private void getGroupDayLessonsMethod() {
    }

    @Pointcut("execution (java.util.List ua.com.foxminded.dao.LessonDAO.getLecturerDayLessons(int, java.time.DayOfWeek))")
    private void getLecturerDayLessonsMethod() {
    }

    @Around("getGroupDayLessonsMethod()")
    Object aroundGetGroupDayLessonsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int groupId = (int) proceedingJoinPoint.getArgs()[0];
        DayOfWeek weekDay = (DayOfWeek) proceedingJoinPoint.getArgs()[1];

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all lessons for a group with id {} which is on a day {}.", groupId, weekDay);
        }

        try {
            Object targetMethod = proceedingJoinPoint.proceed();

            if (targetMethod instanceof List<?>) {
                if (((List<?>) targetMethod).isEmpty()) {
                    LOGGER.warn("There are not any lesson for the group with id {} on a day {}.", groupId, weekDay);
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("For the group with id {} on a day {} there are lessons: {}.", groupId, weekDay,
                                targetMethod);
                    }
                }
            }

            return targetMethod;

        } catch (PersistenceException persistenceException) {
            LOGGER.error("Can't get lessons for a group with id {} on a day {}.", groupId, weekDay,
                    persistenceException);
            throw new DAOException("Can't get day lessons for a group.", persistenceException);
        }
    }

    @Around("getLecturerDayLessonsMethod()")
    Object aroundGetLecturerDayLessonsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int lecturerId = (int) proceedingJoinPoint.getArgs()[0];
        DayOfWeek weekDay = (DayOfWeek) proceedingJoinPoint.getArgs()[1];

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all lessons for a lecturer with id {} on a day {}.", lecturerId, weekDay);
        }

        try {
            Object targetMethod = proceedingJoinPoint.proceed();

            if (targetMethod instanceof List<?>) {

                if (((List<?>) targetMethod).isEmpty()) {
                    LOGGER.warn("There are not any lesson for the lecturer with id {} on a day {}.", lecturerId,
                            weekDay);
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("For the lecturer with id {} on a day {} there are lessons: {}.", lecturerId,
                                weekDay, targetMethod);
                    }
                }
            }

            return targetMethod;
        } catch (PersistenceException persistenceException) {
            LOGGER.error("Can't get lessons for a lecturer with id {} on a day {}.", lecturerId, weekDay,
                    persistenceException);
            throw new DAOException("Can't get day lessons for a lecturer.", persistenceException);
        }
    }
}