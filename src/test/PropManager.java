package test;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.itvgame.model.Prop;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

public class PropManager {

	private GameEngine engine;
	public PlayerProp[] props;

	public PropManager(GameEngine engine) {
		this.engine = engine;
		props = engine.props;
	}

	/* ��ѯ��ҵ��� */
	public void queryProps() {
		initProps(props);
		/*
		 * ServiceWrapper sw = engine.getServiceWrapper(); OwnProp[] pps =
		 * sw.queryOwnPropList();
		 */
		if (pps == null) {
			return;
		}
		for (int i = 0; i < props.length; i++) {
			for (int j = 0; j < pps.length; j++) {
				if (pps[j].getPropId() == props[i].getPropId()) {
					props[i].setNums(pps[j].getCount());
				}
			}
		}

		/*
		 * for(int i=0;i<pps.length;i++){
		 * System.out.println("ID=="+pps[i].getPropId());
		 * System.out.println("count=="+pps[i].getCount()); }
		 */for (int i = 0; i < props.length; i++) {
			System.out.println("����ID==" + props[i].getPropId());
			System.out.println("��������==" + props[i].getNums());
		}
	}

	private void initProps(PlayerProp[] props2) {
		/*
		 * ServiceWrapper sw = engine.getServiceWrapper(); Prop[] ps =
		 * sw.queryGamePropList();
		 */
		props = new PlayerProp[ps.length];
		System.out.println("�����������ݲ���ʼ��������Ϣ,size:" + props.length);
		for (int i = 0; i < ps.length; i++) {
			PlayerProp prop = new PlayerProp();
			prop.setPropId(ps[i].getPropId());
			prop.setName(ps[i].getPropName());
			prop.setPrice(ps[i].getPrice());
			prop.setId(i);
			prop.setNums(0);
			prop.setDesc(ps[i].getDescription());
			prop.setFeeCode(ps[i].getFeeCode());
			props[i] = prop;
		}

		for (int m = 0; m < props.length; m++) {
			System.out.println("propId:" + props[m].getPropId() + ", price:"
					+ props[m].getPrice() + ", name:" + props[m].getName());
		}
	}

	/* ���ݵ���ID��ѯ�õ������� */
	public PlayerProp getPropById(int propId) {
		int len = props.length;
		for (int i = len - 1; i >= 0; i--) {
			if (props[i].getPropId() == propId) {
				return props[i];
			}
		}
		return null;
	}

	public int getPropNumsById(int id) {
		int len = props.length;
		for (int i = len - 1; i >= 0; i--) {
			if (props[i].getPropId() == id) {
				return props[i].getNums();
			}
		}
		return 0;
	}

	public int getPriceById(int propId) {
		int len = props.length;
		for (int i = len - 1; i >= 0; i--) {
			if (props[i].getPropId() == propId) {
				return props[i].getPrice();
			}
		}
		return 0;
	}

	public boolean buyProp(int propId, int propCount, SGraphics g) {
		PlayerProp pp = getPropById(propId);
		// if (engine.getEngineService().getBalance() >= pp.getPrice()) {
		ServiceWrapper sw = engine.getServiceWrapper();
		// sw.purchaseProp(propId, 1, "����"+pp.getName());
		sw.expendTelcomsh(propId, "����" + pp.getName());
		PopupText pt = UIResource.getInstance().buildDefaultPopupText();
		if (sw.isServiceSuccessful()) {
			pt.setText("����" + pp.getName() + "�ɹ�");
		} else {
			pt.setText("����" + pp.getName() + "ʧ��, ԭ��: "
					+ sw.getServiceMessage());

		}
		pt.popup();
		return sw.isServiceSuccessful();
		/*
		 * }else { PopupConfirm pc =
		 * UIResource.getInstance().buildDefaultPopupConfirm();
		 * pc.setText("��Ϸ�Ҳ���,�Ƿ��ֵ"); if (pc.popup() == 0) { StateRecharge
		 * recharge = new StateRecharge(engine); recharge.recharge(); if(g !=
		 * null){ engine.stateGame.show(g); } } return false; }
		 */
	}

	/* ͬ������ */
	public void sysProps() {
		ServiceWrapper sw = engine.getServiceWrapper();
		for (int i = 0; i < props.length; i++) {
			sw.synProps(props[i].getPropId(), props[i].getNums());
		}
		System.out.println("ͬ������:" + sw.isServiceSuccessful());
		for (int i = 0; i < props.length; i++) {
			System.out.println("����ID==" + props[i].getPropId());
			System.out.println("��������==" + props[i].getNums());
		}
	}
}
