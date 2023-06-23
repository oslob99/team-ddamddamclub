package kr.co.ddamddam.upload;

import kr.co.ddamddam.project.api.ProjectApiController;
import kr.co.ddamddam.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {

  private final UploadService uploadService;

  // 이미지파일 데이터를 클라이언트에게 응답처리
  @GetMapping("/load-file")
  public ResponseEntity<?> loadFile(
      @RequestParam("projectIdx") Long projectIdx,
      @RequestParam("boardType") String boardType
  ) {

    log.info("/load-file?projectIdx = {} & boardType = {} ", projectIdx,boardType);

    try {
      String filePath
          = uploadService.getFilePath(projectIdx,boardType);

      File file = new File(filePath);

      if (!file.exists()) {
        return ResponseEntity.notFound().build();
      }

      byte[] fileData = FileCopyUtils.copyToByteArray(file);

      HttpHeaders headers = new HttpHeaders();
      MediaType contentType = findExtensionAndGetMediaType(filePath);
      if (contentType == null) {
        return ResponseEntity.internalServerError()
            .body("발견된 파일은 이미지 파일이 아닙니다.");
      }
      headers.setContentType(contentType);

      log.info("file data !! {}",fileData);

      return ResponseEntity.ok()
          .headers(headers)
          .body(fileData);

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError()
          .body("파일을 찾을 수 없습니다.");
    }

  }

  private MediaType findExtensionAndGetMediaType(String filePath) {
    String ext
        = filePath.substring(filePath.lastIndexOf(".") + 1);

    switch (ext.toUpperCase()) {
      case "JPG": case "JPEG":
        return MediaType.IMAGE_JPEG;
      case "PNG":
        return MediaType.IMAGE_PNG;
      case "GIF":
        return MediaType.IMAGE_GIF;
      default:
        return null;
    }  }
}
