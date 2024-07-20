package service.Impl;

import com.joyee.feign.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ConnectKafkaService;

@Service
public class ConnectKafkaImpl implements ConnectKafkaService {

    @Autowired
    private KafkaService kafkaService;

    public String getKafkaReturn (){
        return kafkaService.sendNormalMessage("");
    }
}
