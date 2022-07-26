package com.pp.service;

import com.pp.controller.util.R;

public interface ISubmissionService {

    R getExperimentSubmission(String experiment_id);

    R gradeExperimentSubmission(Integer experimentId, Integer studentId, float Score);

    R getExperimentSubmissionByScore(Boolean ifScore,String experimentID);

    R queryForStudent(String studentID,String experimentID);
}
