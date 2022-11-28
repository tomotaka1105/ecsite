package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	@Autowired
	private GoodsRepository goodsRepos;
	
	@Autowired
	private UserRepository userRepos;
	
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}

	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model m) {
		User user = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		if (user == null) {
            m.addAttribute("errMessage", "ユーザー名またはパスワードが違います。");
            return "welcome";
        }

		if (user.getIsAdmin() == 0) {
            m.addAttribute("errMessage", "管理者ではありません。");
            return "welcome";
        } 
		
		if (user != null && user.getIsAdmin() == 1) {
			boolean isAdmin = user.getIsAdmin() != 0;
				if (isAdmin) {
					m.addAttribute("nonMessage","nonMessage");
					List<Goods> goods = goodsRepos.findAll();
					m.addAttribute("userName",user.getUserName());
					m.addAttribute("password",user.getPassword());
					m.addAttribute("goods",goods);
				} return "welcome";
		} return "welcome";

	}
	

	@RequestMapping("/goodsMst")
	public String goodsMst(LoginForm form, Model m) {
		m.addAttribute("userName", form.getUserName());
		m.addAttribute("password", form.getPassword());
		
		return "goodsmst";
	}
	
	@RequestMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsRepos.saveAndFlush(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsRepos.deleteById(f.getId());
		} catch (IllegalArgumentException e) {
			return "-1";
		}
		
		return "1";
	}
}
