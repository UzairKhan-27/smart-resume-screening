package com.uzair.smart_resume_screening.service;

import com.uzair.smart_resume_screening.dto.ResumeResponse;
import com.uzair.smart_resume_screening.exception.PersonNotFoundException;
import com.uzair.smart_resume_screening.mapper.ResumeMapper;
import com.uzair.smart_resume_screening.model.Person;
import com.uzair.smart_resume_screening.model.Resume;
import com.uzair.smart_resume_screening.repo.PersonRepo;
import com.uzair.smart_resume_screening.repo.ResumeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ParsingService parsingService;
    private final PersonRepo personRepo;
    private final ResumeRepo resumeRepo;
    private final ResumeMapper mapper;
    public Resume parseResume(MultipartFile file) throws IOException {
        String text = parsingService.readFile(file);
        text = text.toLowerCase();
        text = text.replaceAll("\\r\\n"," ");
        HashMap<String,Integer> resumeSections = parsingService.initializeResumeSections();
        parsingService.findResumeSectionStartingIndex(resumeSections,text);
        Map<String, Integer> sortedResumeSections = parsingService.sortResumeSectionsByStartingIndex(resumeSections);
        HashMap<String,String> resumeSectionsContent = parsingService.initializeResumeSectionsContent();
        parsingService.readResumeSectionsContent(sortedResumeSections,resumeSectionsContent,text);
        parsingService.removeSectionHeadingFromContent(resumeSectionsContent);
        resumeSectionsContent.entrySet().removeIf(entry -> entry.getValue().trim().isEmpty());
        Resume resume = resumeRepo.save(mapper.toEntity(resumeSectionsContent));
        return resume;
    }
}
