package kr.co.ddamddam.project.service;


import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.ProjectLike;
import kr.co.ddamddam.project.repository.ProjectLikeRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjectLikeService {

  private final ProjectLikeRepository projectLikeRepository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  public void handleLike(Long userIdx, Long projectIdx) {
    User user = getUser(userIdx);
    Project project = getProject(projectIdx);

    ProjectLike projectLike = projectLikeRepository.findByUserAndProject(user, project);

    if (projectLike == null) {
      // 좋아요 추가
      projectLike = ProjectLike.builder()
          .user(user)
          .project(project)
          .build();
      projectLikeRepository.save(projectLike);

      // 좋아요 수 증가
      project.setLikeCount(project.getLikeCount() + 1);
    } else {
      // 좋아요 취소
      projectLikeRepository.delete(projectLike);

      // 좋아요 수 감소
      project.setLikeCount(project.getLikeCount() - 1);
    }
    projectRepository.save(project);
  }

  public boolean checkIfLiked(Long userIdx, Long projectIdx) {
    // 사용자의 userIdx와 프로젝트의 projectIdx를 기반으로 좋아요 여부를 조회합니다.
    ProjectLike projectLike
        = projectLikeRepository.findByUserAndProject(
        getUser(userIdx), getProject(projectIdx)
    );

    // projectLike가 null이 아니라면 좋아요가 선택되어 있다고 판단합니다.
    return projectLike != null;
  }

  private Project getProject(Long projectIdx) {
    Project project = projectRepository.findById(projectIdx)
        .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));
    return project;
  }

  private User getUser(Long userIdx) {
    User user = userRepository.findById(userIdx)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
    return user;
  }

}
