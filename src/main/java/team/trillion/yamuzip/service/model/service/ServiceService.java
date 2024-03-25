package team.trillion.yamuzip.service.model.service;

import org.springframework.stereotype.Service;
import team.trillion.yamuzip.service.model.dao.ServiceMapper;
import team.trillion.yamuzip.service.model.dto.*;

import java.util.List;

@Service
public class ServiceService {
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    public List<ServiceDTO> findAllService() {
        return serviceMapper.findAllService();
    }

    public List<ServiceDTO> findInfoService() {
        return serviceMapper.findInfoService();
    }

    public List<ImageDTO> getImages() {
        return serviceMapper.getImages();
    }

    public List<OptionDTO> getOptions() {
        return serviceMapper.getOptions();
    }

    public List<ReviewDTO> getReviews() {
        return serviceMapper.getReviews();
    }

    public List<CsDTO> getCs() {
        return serviceMapper.getCs();
    }
}
