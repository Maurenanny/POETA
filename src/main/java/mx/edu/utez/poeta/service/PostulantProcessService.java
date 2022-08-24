package mx.edu.utez.poeta.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.TemplateException;
import mx.edu.utez.poeta.entity.PostulantProcess;
import mx.edu.utez.poeta.entity.Vacancies;
import mx.edu.utez.poeta.repository.IPostulantProcessRepository;
import mx.edu.utez.poeta.repository.IVacanciesRepository;

@Service
@Transactional
public class PostulantProcessService {

    @Autowired
    private IPostulantProcessRepository postulantProcessRepository;

    @Autowired
    private IVacanciesRepository vacanciesRepository;

    @Autowired
    private EmailService emailService;

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllProcesses() {
        return postulantProcessRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostulantProcess findProcessById(long id) {
        return postulantProcessRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllUserProcesses(long id) {
        return postulantProcessRepository.findAllProcessesByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllUserFavoriteProcesses(long id) {
        return postulantProcessRepository.findAllFavoriteProcessesByUserId(id);
    }

    public PostulantProcess save(PostulantProcess obj) {
        PostulantProcess tmp = postulantProcessRepository.save(obj);
        if (tmp != null) {
            return tmp;
        }
        return null;
    }

    public boolean delete(long id) {
        boolean flag = false;
        PostulantProcess tmp = findProcessById(id);
        if (tmp != null) {
            postulantProcessRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

    public List<PostulantProcess> findAllProcessFromVacancy(long id, int type) {
        switch (type) {
            case 1: //Postulados
            return postulantProcessRepository.findAllProcessFromVacancyStageOne(id);
            case 2: //Para Entrevistar
            return postulantProcessRepository.findAllProcessFromVacancyStageTwo(id);
            case 3: //Idóneos
            return postulantProcessRepository.findAllProcessFromVacancyStageThree(id);
            case 4: //Contratado
            return postulantProcessRepository.findAcceptedPostulantFromVacancy(id);
        }
        return null;
    }

    public List<PostulantProcess> findPostulantProcessesByFilter(long id, int type) {
        switch (type) {
            case 1:
            return postulantProcessRepository.findAllProcessesByUserId(id);
            case 2:
            return postulantProcessRepository.findAllFavoriteProcessesByUserId(id);
            case 3:
            return postulantProcessRepository.findAllRejectedProcessesByUserId(id);
            case 4:
            return postulantProcessRepository.findAllAcceptedProcessesByUserId(id);
        }
        return null;
    }

    public boolean selectProcessWinner(long id) throws MessagingException, IOException, TemplateException {
        PostulantProcess process = findProcessById(id);
        Vacancies tmpVacancy = process.getVacant();
        tmpVacancy.setStatus(false);
        vacanciesRepository.save(tmpVacancy);
        List<PostulantProcess> processList = postulantProcessRepository.findAllPostulantsForRejection(process.getVacant().getId(), process.getPostulant().getId());
        for (PostulantProcess p : processList) {
            emailService.sendEmail(p, 2);
        }
        postulantProcessRepository.CloseProcess(process.getVacant().getId(), process.getPostulant().getId());
        emailService.sendEmail(process, 1);
        process.setStatus(5);
        save(process);
        return true;
    }
    
}
