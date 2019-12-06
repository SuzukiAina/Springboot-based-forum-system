package rqchen.fkbbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.mapper.Thememapper;

import java.util.List;

@Service
public class ThemeService {
    @Autowired
    Thememapper themeMapper;

    public List<Theme> themeList(){
        return themeMapper.getAllTheme();
    }

    public int insert(Theme theme){
        return themeMapper.InsertTheme(theme);
    }

    public int delete(Integer id){
        return themeMapper.delThemeById(id);
    }

    public int update(Theme theme){
        return themeMapper.UpdateTheme(theme);
    }

    public Theme getById(Integer id) {
        return themeMapper.getThemeById(id);
    }

    public List<Theme> mythemeList(Integer id){ return themeMapper.getThemeByUserId(id);}

    public int getCount(){return themeMapper.getThemeCount();}

    public int lastreply(Reply reply){return themeMapper.UpdateLastReply(reply);}

    public String themeTitle(int theme_id){return  themeMapper.getThemeTitle(theme_id);}
}
