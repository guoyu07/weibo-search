package org.conan.search.weibo.action.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.conan.base.service.PageInObject;
import org.conan.base.service.SpringService;
import org.conan.base.util.MyDate;
import org.conan.search.weibo.action.LoadService;
import org.conan.search.weibo.action.WeiboActionService;
import org.conan.search.weibo.model.LoadFrequenceDTO;
import org.conan.search.weibo.model.UserRelateDTO;
import org.conan.search.weibo.service.LoadFrequenceService;
import org.conan.search.weibo.service.UserRelateService;
import org.conan.search.weibo.service.UserService;
import org.conan.search.weibo.util.WeiboTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

@Service
public class LoadServiceImpl implements LoadService {

    final private static Logger log = Logger.getLogger(Log.class.getName());

    @Autowired
    UserService userService;
    @Autowired
    UserRelateService userRelateService;
    @Autowired
    LoadFrequenceService loadFrequenceService;
    @Autowired
    WeiboActionService action;

    @Override
    public void fans(long uid, String token) throws WeiboException {
        fans(uid, SpringService.WEIBO_LOAD_COUNT_200, token);
    }

    @Override
    public void fans(long uid, int n, String token) throws WeiboException {
        if (!loadLimit(uid, SpringService.LIMIT_WEIBO_LOAD_FANS)) {
            log.info(uid + " Load fans limit! Try later.");
            return;
        }

        Friendships fm = new Friendships();
        fm.client.setToken(token);

        int count = n < SpringService.WEIBO_LOAD_COUNT_200 ? n : SpringService.WEIBO_LOAD_COUNT_200;
        int i = 0;
        do {
            UserWapper users = fm.getFollowersById(String.valueOf(uid), (int) count, (int) i);
            for (User u : users.getUsers()) {
                try {
                    userService.insertUser(WeiboTransfer.user(u));
                } catch (Exception e) {
                    log.debug("{user:" + uid + "}" + e.getMessage());
                }

                try {
                    userRelateService.insertUserRelate(new UserRelateDTO(uid, Long.parseLong(u.getId())));
                } catch (Exception e) {
                    log.debug("{user:" + uid + ",fans:" + u.getId() + "}" + e.getMessage());
                }
            }
            i += count;
            count = (int) users.getTotalNumber() - i > SpringService.WEIBO_LOAD_COUNT_200 ? SpringService.WEIBO_LOAD_COUNT_200 : (int) users.getTotalNumber() - i;
            n = (int) users.getTotalNumber() > n ? n : (int) users.getTotalNumber();
            log.info(uid + " LOAD fans count: " + i + "/" + n);
        } while (i < n);

        loadFrequenceService.insertLoadFrequence(new LoadFrequenceDTO(uid, SpringService.LIMIT_WEIBO_LOAD_FANS, null));
    }

    @Override
    public void follow(long uid, String token) throws WeiboException {
        follow(uid, SpringService.WEIBO_LOAD_COUNT_200, token);
    }

    @Override
    public void follow(long uid, int n, String token) throws WeiboException {
        if (!loadLimit(uid, SpringService.LIMIT_WEIBO_LOAD_FOLLOW)) {
            log.info(uid + " Load follow limit! Try later.");
            return;
        }

        Friendships fm = new Friendships();
        fm.client.setToken(token);

        int count = n < SpringService.WEIBO_LOAD_COUNT_200 ? n : SpringService.WEIBO_LOAD_COUNT_200;
        int i = 0;
        do {
            UserWapper users = fm.getFriendsById(String.valueOf(uid), (int) count, (int) i);
            for (User u : users.getUsers()) {
                try {
                    userService.insertUser(WeiboTransfer.user(u));
                } catch (Exception e) {
                    log.debug("{user:" + uid + "}" + e.getMessage());
                }
                try {
                    userRelateService.insertUserRelate(new UserRelateDTO(Long.parseLong(u.getId()), uid));
                } catch (Exception e) {
                    log.debug("{user:" + u.getId() + ",fans:" + uid + "}" + e.getMessage());
                }
            }
            i += count;
            count = (int) users.getTotalNumber() - i > SpringService.WEIBO_LOAD_COUNT_200 ? SpringService.WEIBO_LOAD_COUNT_200 : (int) users.getTotalNumber() - i;
            n = (int) users.getTotalNumber() > n ? n : (int) users.getTotalNumber();
            log.info(uid + " LOAD follow count: " + i + "/" + n);
        } while (i < n);
        loadFrequenceService.insertLoadFrequence(new LoadFrequenceDTO(uid, SpringService.LIMIT_WEIBO_LOAD_FOLLOW, null));
    }

    /**
     * @return true允许,false不允许
     */
    @Override
    public boolean loadLimit(long uid, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("type", type);
        List<LoadFrequenceDTO> list = loadFrequenceService.getLoadFrequencesPaging(map, new PageInObject(0, 1, "id", "desc")).getList();
        if (list.size() > 0) {
            long diff = MyDate.diffSecs(new Date(), list.get(0).getCreate_date());
            if (diff <= SpringService.TIME_DAY * 7) {
                return false;
            }
        }
        return true;
    }

}
