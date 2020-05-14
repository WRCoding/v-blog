package top.lpepsi.vblog.controller.archive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.lpepsi.vblog.dto.Response;
import top.lpepsi.vblog.service.archive.ArchiveService;

/**
 * @program: v-blog
 * @description: 归档
 * @author: 林北
 * @create: 2020-04-21 15:35
 **/
@RestController
@Slf4j
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/archive")
    public Response archive(){
        return archiveService.getYear();
    }
    @GetMapping("/archive/{year}")
    public Response getBlogsByYear(@PathVariable("year")Integer year){
        return archiveService.getBlogsByYear();
    }
}
