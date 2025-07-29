package com.uzair.smart_resume_screening.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParsingService {

    public String readFile(MultipartFile file) throws IOException {
        PDDocument document = Loader.loadPDF(file.getInputStream().readAllBytes());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    public HashMap<String,Integer> initializeResumeSections(){
        HashMap<String,Integer> resumeSections = new HashMap<>();
        resumeSections.put("skills",-1);
        resumeSections.put("education",-1);
        resumeSections.put("experience",-1);
        resumeSections.put("certifications",-1);
        resumeSections.put("projects",-1);
        resumeSections.put("hobbies",-1);
        resumeSections.put("summary",-1);
        resumeSections.put("objective",-1);
        resumeSections.put("achievements",-1);
        resumeSections.put("personal",-1);
        resumeSections.put("background",-1);
        resumeSections.put("award",-1);
        resumeSections.put("accomplishment",-1);
        resumeSections.put("contact",-1);
        resumeSections.put("about",-1);

        return resumeSections;
    }

    public void findResumeSectionStartingIndex(HashMap<String, Integer> resumeSections,
                                                                  String text){
        for(String i:resumeSections.keySet()){
            System.out.println(i);
            System.out.println(resumeSections.get(i));
            int index = text.indexOf(i);
            if(index != -1) {
                resumeSections.put(i, index);
            }
        }
    }

    public Map<String, Integer> sortResumeSectionsByStartingIndex(HashMap<String,Integer> resumeSections){
        Map<String,Integer> sortedResumeSections = resumeSections.entrySet()
                .stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue) -> oldValue, LinkedHashMap::new
                ));
        sortedResumeSections.entrySet().removeIf(entry -> entry.getValue() == -1);
        return sortedResumeSections;
    }

    public HashMap<String, String> initializeResumeSectionsContent() {
        HashMap<String,String> resumeSections = new HashMap<>();
        resumeSections.put("skills","");
        resumeSections.put("education","");
        resumeSections.put("experience","");
        resumeSections.put("projects","");
        resumeSections.put("certifications","");
        resumeSections.put("achievements","");
        return resumeSections;
    }

    public void readResumeSectionsContent(Map<String, Integer> sortedResumeSections,
                                          HashMap<String, String> resumeSectionsContent,
                                          String text){

        int resultSize = sortedResumeSections.size();
        int count = 0;
        List<Integer> indexList = new ArrayList<>(sortedResumeSections.values());
        for(String i : sortedResumeSections.keySet()){

            int beginIndex = indexList.get(count);
            if(count == resultSize - 1){
                String sectionSubstring = text.substring(beginIndex);
                resumeSectionsContent.put(i,sectionSubstring);
                System.out.println(sectionSubstring);
            }
            else {
                int endIndex = indexList.get(count+1);
                String sectionSubstring = text.substring(beginIndex, endIndex);
                resumeSectionsContent.put(i,sectionSubstring);
                System.out.println(sectionSubstring);
            }
            count++;

        }
    }

    public void removeSectionHeadingFromContent(HashMap<String, String> resumeSectionsContent) {

        for(String i:resumeSectionsContent.keySet()){
            String sectionName = resumeSectionsContent.get(i);
            sectionName = sectionName.replaceAll(i,"");
            sectionName = sectionName.replaceFirst("\r\n","");
            resumeSectionsContent.put(i, sectionName);
        }
    }

    public List<String> extractSkills(String skills) {
        String[] skillsArray = skills.split("\\r\\n|\\n|\\t|,|:|;|\\(|\\)");
        List<String> skillsList = new ArrayList<>(Arrays.asList(skillsArray));
        skillsList = skillsList.stream()
                .map(String::strip)
                .filter(s->!s.isEmpty())
                .collect(Collectors.toList());
        return skillsList;
    }
}
