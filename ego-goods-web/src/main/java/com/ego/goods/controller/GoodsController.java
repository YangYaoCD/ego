package com.ego.goods.controller;

import com.ego.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author YangYao
 * @date 2020/9/17 21:22
 * @Description
 */
@Controller
@RequestMapping("item")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/{id}.html")
    public String item(@PathVariable("id") Long id, Model model) {
        Map<String, Object> modelMap = goodsService.loadModel(id);
        model.addAllAttributes(modelMap);

        //异步生成静态页面
        goodsService.buildStaticHtml(modelMap,id);
        return "item";
    }
}
