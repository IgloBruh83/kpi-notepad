package edu.kpinotepad.services;

import edu.kpinotepad.dto.LinkDTO;
import edu.kpinotepad.dto.SubjectDTO;
import edu.kpinotepad.dto.TeacherSubjectDTO;
import edu.kpinotepad.models.Link;
import edu.kpinotepad.models.Subject;
import edu.kpinotepad.models_asoc.TeacherSubject;
import edu.kpinotepad.repositories.LinkRepository;
import edu.kpinotepad.repositories.SubjectRepository;
import edu.kpinotepad.repositories.TeacherSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final LinkRepository linkRepository;

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAllByOrderByGeneralDescNameAsc();
        List<Long> ids = subjects.stream().map(Subject::getId).collect(Collectors.toList());

        Map<Long, List<TeacherSubject>> teachersMap = teacherSubjectRepository.findAllBySubjectIdIn(ids)
                .stream().collect(Collectors.groupingBy(ts -> ts.getSubject().getId()));

        Map<Long, List<Link>> linksMap = linkRepository.findAllBySubjectIdIn(ids)
                .stream().collect(Collectors.groupingBy(l -> l.getSubject().getId()));

        return subjects.stream()
                .map(s -> mapToDTO(s, teachersMap.get(s.getId()), linksMap.get(s.getId())))
                .collect(Collectors.toList());
    }

    private SubjectDTO mapToDTO(Subject s, List<TeacherSubject> tsLinks, List<Link> links) {
        return SubjectDTO.builder()
                .subjectId(s.getId())
                .subjectName(s.getName())
                .general(s.getGeneral())
                .generalInfo(s.getGeneralInfo())
                .conditions(s.getConditions())
                .officialLinks(links == null ? List.of() : links.stream()
                        .map(l -> new LinkDTO(l.getLabel(), l.getFullLink()))
                        .collect(Collectors.toList()))
                .teachers(tsLinks == null ? List.of() : tsLinks.stream()
                        .map(ts -> TeacherSubjectDTO.builder()
                                .teacherId(ts.getTeacher().getId())
                                .teacherName(ts.getTeacher().getDisplayName())
                                .teacherAvatarUrl(ts.getTeacher().getAvatarUrl())
                                .teacherRole(ts.getRole().name())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}