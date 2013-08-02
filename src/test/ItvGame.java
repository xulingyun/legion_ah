package test;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.MIDlet;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * ���պ��δ����Ʒ�/�浵�����ļ�
 * 
 * @author yorkey
 * 
 */
public class ItvGame {

	/** iTV�˺�(���а������Ǹ�itv�˺�) */
	public static String USER_ID = null;

	/** ��Ϸid */
	private static String GAMEID = null;

	/** ������ip��ַ */
	private static String SERVERIP = null;

	/** cp�̵ı�� */
	private static String CPID = null;

	/** ��Ȩ�û���Ϣ�ӿڣ���Ϸ��ʼ����ʱ�����һ�Σ� */
	public static String URL_BSCS_USER_QUERY = null;

	/** ���߹���ӿ� */
	public static String URL_BSCS_PROPS_ORDER = null;

	/** �浵 */
	public static String URL_BSCS_CROSS_SAVEORUPDATE = null;

	/** ��ѯȡ�ô浵��Ϣ */
	public static String URL_BSCS_CROSS_QUERY = null;

	/** ���´洢���� */
	public static String URL_BSCS_RANK_SAVEORUPDATE = null;

	/** ��ѯ�������а�ӿ� */
	public static String URL_BSCS_RANK_QUERY = null;

	/** ��Ϸʱ�� */
	public static String URL_BSCS_TIME = null;

	/** ��浵 */
	public static String URL_BSCS_CROSS_SAVERECORD_MULTY = null;

	/** ���൵ */
	public static String URL_BSCS_CROSS_QUERY_MULTY = null;

	public ItvGame() {

	}

	/**
	 * ��Ȩ�û���Ϣ�ӿڣ���Ϸ��ʼ����ʱ�����һ�Σ�
	 * 
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper init(MIDlet midlet) throws XmlPullParserException {
		if (midlet == null) {
			return new RespWapper(false, "�봫��MIDlet����", "null", "null");
		}
		USER_ID = midlet.getAppProperty("userid");
		if (USER_ID == null) {
			return new RespWapper(false,
					"��ȡuseid��������jad�м������,����,useid: 10000024", "null", "null");
		}
		GAMEID = midlet.getAppProperty("gameid");
		if (GAMEID == null) {
			return new RespWapper(false, "��ȡgameid��������jad�м������,����,gameid: 15",
					"null", "null");
		}
		SERVERIP = midlet.getAppProperty("serverip");
		if (SERVERIP == null) {
			return new RespWapper(
					false,
					"��ȡserverip��������jad�м������,����,serverip: http://61.191.45.166:8080",
					"null", "null");
		}
		CPID = midlet.getAppProperty("cpid");
		if (CPID == null) {
			return new RespWapper(false,
					"��ȡcpid��������jad�м������,����,cpid: hyCpID00001", "null", "null");
		}

		URL_BSCS_PROPS_ORDER = SERVERIP
				+ "/gameHall/webservice/GameorderAction?requestmethod";
		URL_BSCS_CROSS_SAVEORUPDATE = SERVERIP
				+ "/gameRecord/SaveRecordAction?";
		URL_BSCS_CROSS_QUERY = SERVERIP + "/gameRecord/ReadRecordAction?";
		URL_BSCS_RANK_SAVEORUPDATE = SERVERIP + "/gameRecord/SavePointAction?";
		URL_BSCS_RANK_QUERY = SERVERIP + "/gameRecord/GetRankAction?";
		URL_BSCS_TIME = SERVERIP + "/gameRecord/GetSystimeAction";
		URL_BSCS_CROSS_SAVERECORD_MULTY = SERVERIP
				+ "/gameRecord/SaveRecordMulty?";
		URL_BSCS_CROSS_QUERY_MULTY = SERVERIP + "/gameRecord/ReadRecordMulty?";

		return new RespWapper(true, "��Ϸ������ʼ���ɹ�", "0", "null");
	}

	/**
	 * ��ȡ���
	 * 
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper goldQuery() throws XmlPullParserException {
		String sendURL = URL_BSCS_PROPS_ORDER + "=goldCount&userID=" + USER_ID;
		sendURL = "" + sendURL;
		return http(sendURL, "result", "goldCount", true);
	}

	/**
	 * �õ���������ʱ��
	 * 
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper timeQuery() throws XmlPullParserException {
		String sendURL = URL_BSCS_TIME;
		sendURL = "" + sendURL;
		RespWapper r = http(sendURL, "code", "Time", false);
		if (r._ret) {
			String time = r.get_message();
			r.set_time(time);
		}

		return r;
	}

	/**
	 * ��ֵ�ӿ�
	 * 
	 * @param password
	 * @param price
	 * @param description
	 *            ˵��
	 * @return
	 * @throws XmlPullParserException
	 */

	public RespWapper Recharge(String password, String price, String description)
			throws XmlPullParserException {
		if ((null == price || "".equals(price))
				|| (null == description || "".equals(description))) {
			return new RespWapper(false, "�봫���ֵ�ļ۸��˵��", "null", "null");
		}
		String sendURL = URL_BSCS_PROPS_ORDER + "=recharge&cpid=" + CPID
				+ "&userID=" + USER_ID + "&password=" + password + "&price="
				+ price + "&description=" + description + "&userIDType=" + "0";
		sendURL = "" + sendURL;
		return http(sendURL, "result", "message", true);
	}

	/**
	 * ���߹���ӿ�
	 * 
	 * @param propID
	 *            ������
	 * @return
	 * @throws XmlPullParserException
	 */

	public RespWapper propsOrder(String propID) throws XmlPullParserException {
		if ((null == propID || "".equals(propID))) {
			return new RespWapper(false, "�봫�������", "null", "null");
		}
		String sendURL = URL_BSCS_PROPS_ORDER + "=gameorder&propID=" + propID
				+ "&userID=" + USER_ID;
		sendURL = "" + sendURL;
		return http(sendURL, "result", "message", true);
	}

	/**
	 * �浵
	 * 
	 * @param crossCodePoints
	 *            �Զ�����������(���Դ������Ӣ�ġ���ĸ�����š�|)
	 * @return ��װ��
	 * @throws XmlPullParserException
	 */
	public RespWapper crossSaveOrUpdate(String crossCodePoints)
			throws XmlPullParserException {
		if ((null == crossCodePoints || "".equals(crossCodePoints))) {
			return new RespWapper(false, "�봫�����Ĵ浵��Ϣ", "null", "null");
		}
		String sendURL = URL_BSCS_CROSS_SAVEORUPDATE + "userid=" + USER_ID
				+ "&gameid=" + GAMEID + "&crosscodepoints=" + crossCodePoints;
		sendURL = "" + sendURL;
		return http(sendURL, "code", "message", false);
	}

	/**
	 * �浵(�ɴ����)
	 * 
	 * @param crossCodePoints
	 *            �Զ�����������(���Դ������Ӣ�ġ���ĸ�����š�|)
	 * @param keyCode
	 *            (�浵��ʶ)
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper crossSaveRecordMulty(String crossCodePoints, int keyCode)
			throws XmlPullParserException {
		if ((null == crossCodePoints || "".equals(crossCodePoints))) {
			return new RespWapper(false, "�봫�����Ĵ浵��Ϣ", "null", "null");
		}
		String sendURL = URL_BSCS_CROSS_SAVERECORD_MULTY + "userid=" + USER_ID
				+ "&gameid=" + GAMEID + "&crosscodepoints=" + crossCodePoints
				+ "&keyCode=" + keyCode;
		sendURL = "" + sendURL;
		return http(sendURL, "code", "message", false);
	}

	/**
	 * ȡ�ô浵��Ϣ
	 * 
	 * @param account
	 * @param productCode
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper crossquery() throws XmlPullParserException {

		String sendURL = URL_BSCS_CROSS_QUERY + "gameid=" + GAMEID + "&userid="
				+ USER_ID;
		sendURL = "" + sendURL;
		RespWapper r = http(sendURL, "code", "crossCodePoints", false);
		if (r._ret) {
			String crossCodePoints = r.get_message();

			r.setCrossCodePoints(crossCodePoints);
		}

		return r;
	}

	/**
	 * �Ӷ���浵�в�ѯĳһ���浵
	 * 
	 * @param keyCode
	 *            �浵��ʶ
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper crossQueryMulty(int keyCode)
			throws XmlPullParserException {
		String sendURL = URL_BSCS_CROSS_QUERY_MULTY + "gameid=" + GAMEID
				+ "&userid=" + USER_ID + "&keyCode=" + keyCode;
		sendURL = "" + sendURL;
		RespWapper r = http(sendURL, "code", "crossCodePoints", false);
		if (r._ret) {
			String crossCodePoints = r.get_message();

			r.setCrossCodePoints(crossCodePoints);
		}
		return r;
	}

	/**
	 * ���´洢����
	 * 
	 * @param account
	 * @param productCode
	 * @param rankPoints
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper rankSaveOrUpdate(String rankPoints)
			throws XmlPullParserException {
		if (("".equals(rankPoints) || null == rankPoints)) {
			return new RespWapper(false, "�봫���ϴ��Ļ���", "null", "null");
		}
		if (!(String2Int(rankPoints))) {
			return new RespWapper(false, "���ֱ�����int����", "null", "null");
		}
		String sendURL = URL_BSCS_RANK_SAVEORUPDATE + "userid=" + USER_ID
				+ "&gameid=" + GAMEID + "&userscore=" + rankPoints;
		sendURL = "" + sendURL;
		return http(sendURL, "code", "message", false);
	}

	/**
	 * ��ѯ�������а�ӿ�
	 * 
	 * @param account
	 * @param productCode
	 * @return
	 * @throws XmlPullParserException
	 */
	public RespWapper rankQuery() throws XmlPullParserException {

		String sendURL = URL_BSCS_RANK_QUERY + "userid=" + USER_ID + "&gameid="
				+ GAMEID;
		sendURL = "" + sendURL;
		RespWapper r = http(sendURL, "code", "rankOwn", false);
		if (r._ret) {
			String myRankNo = r.get_message();
			RankInfo[] list = getRank(httpGetInputStream(sendURL));

			r.setMyRankNo(myRankNo);
			r.setList(list);
		}
		RespWapper r1 = http(sendURL, "code", "rankSelf", false);
		if (r1._ret) {
			String myScore = r1.get_message();

			r.setMyScore(myScore);
		}

		return r;
	}

	private InputStream httpGetInputStream(String sendURL)
			throws XmlPullParserException {

		HttpConnection conn = null;
		InputStream is = null;
		try {
			conn = (HttpConnection) Connector.open(sendURL);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod(HttpConnection.GET);
			is = conn.openInputStream();
		} catch (Exception e) {
		}
		return is;
	}

	/**
	 * ��װ������httpͨ�ŷ���
	 * 
	 * @param sendURL
	 * @param code
	 * @param unknown
	 *            ��Ҫ���ص���Ϣ
	 * @param isgoldCount
	 *            �Ƿ�Ҫ�������
	 * @return
	 * @throws XmlPullParserException
	 */
	private RespWapper http(String sendURL, String code, String unknown,
			boolean isgoldCount) throws XmlPullParserException {

		RespWapper respWapper = new RespWapper();// TODO

		HttpConnection conn = null;
		InputStream is = null;
		try {
			conn = (HttpConnection) Connector.open(sendURL);
			System.out.println("url:" + sendURL);
			if (conn == null) {
				return new RespWapper(false, "���ܴ���ͨ���������", "null", "null");
			}
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod(HttpConnection.GET);
			is = conn.openInputStream();

			if (conn.getResponseCode() == HttpConnection.HTTP_OK) {
				if (isgoldCount) {
					respWapper = getTextByName(is, code, unknown, true);
				} else {
					respWapper = getTextByName(is, code, unknown, false);
				}

				// String message = this.getTextByName(get(sendURL), _message);
				if (respWapper.get_code().equals("0")) {
					return new RespWapper(true, respWapper.get_unknown(),
							respWapper.get_code(), respWapper.get_goldCount());
				} else {
					return new RespWapper(false, respWapper.get_unknown(),
							respWapper.get_code(), respWapper.get_goldCount());
				}
			} else {
				return new RespWapper(false, "ͨ��ʧ��", "null", "null");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RespWapper(false, "δ֪����", "null", "null");
	}

	private boolean String2Int(String value) {

		try {
			Integer.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private RespWapper getTextByName(InputStream is, String param1,
			String param2, boolean isgoldCount) throws XmlPullParserException {
		RespWapper respWapper = new RespWapper();
		try {

			boolean isParam1Run = false;
			boolean isParam2Run = false;
			boolean isParam3Run = false;
			// 1ʵ����
			KXmlParser parser = new KXmlParser();
			parser.setInput(is, "utf-8");

			// 2�����¼�����
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {// ���¼����Ͳ�Ϊ�ĵ�ĩβʱ
				if (isgoldCount) {
					switch (eventType) {// �ж��¼�����
					case XmlPullParser.START_TAG:// Ϊ��ʼ���ʱ-�պϱ�ǩ��ʼS
						// System.out.print("<" + parser.getName()+">");//
						// �����ʼ���
						if (param1.equals(parser.getName())) {
							parser.next();
							respWapper.set_code(parser.getText());
							isParam1Run = true;
							if (isParam2Run && isParam3Run) {
								return respWapper;
							}
						}
						if (param2.equals(parser.getName())) {
							if ("goldCount".equals(param2)) {
								parser.next();
								respWapper.set_goldCount(parser.getText());
								isParam2Run = true;
								if (isParam1Run && isParam3Run) {
									return respWapper;
								}
							} else {
								parser.next();
								respWapper.set_unknown(parser.getText());
								isParam2Run = true;
								if (isParam1Run && isParam3Run) {
									return respWapper;
								}
							}

						}
						if ("goldCount".equals(parser.getName())) {
							parser.next();
							respWapper.set_goldCount(parser.getText());
							isParam3Run = true;
							if (isParam2Run && isParam1Run) {
								return respWapper;
							}
						}
						break;

					case XmlPullParser.END_TAG: // Ϊ�������ʱ-�պϱ�ǩ����E
						// System.out.print("</" + parser.getName() + ">");
						break;

					case XmlPullParser.TEXT:// Ϊ�ĵ�����ʱ
						// System.out.print(parser.getText());
						break;

					case XmlPullParser.END_DOCUMENT:
						return respWapper;
					}
				} else {
					switch (eventType) {// �ж��¼�����
					case XmlPullParser.START_TAG:// Ϊ��ʼ���ʱ-�պϱ�ǩ��ʼS
						// System.out.print("<" + parser.getName()+">");//
						// �����ʼ���
						if (param1.equals(parser.getName())) {
							parser.next();
							respWapper.set_code(parser.getText());
							isParam1Run = true;
							if (isParam2Run) {
								return respWapper;
							}
						}
						if (param2.equals(parser.getName())) {
							parser.next();
							respWapper.set_unknown(parser.getText());
							isParam2Run = true;
							if (isParam1Run) {
								return respWapper;
							}
						}
						break;

					case XmlPullParser.END_TAG: // Ϊ�������ʱ-�պϱ�ǩ����E
						// System.out.print("</" + parser.getName() + ">");
						break;

					case XmlPullParser.TEXT:// Ϊ�ĵ�����ʱ
						// System.out.print(parser.getText());
						break;

					case XmlPullParser.END_DOCUMENT:
						return respWapper;
					}
				}

				eventType = parser.next();
			}
			// System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respWapper;
	}

	private RankInfo[] getRank(InputStream is) throws XmlPullParserException {// �ӿ�Ĭ�Ϸ���10������
		int arrIndex = 0;
		int temp = 0;
		RankInfo[] rankInfo = new RankInfo[10];
		for (int i = 0; i < rankInfo.length; i++) {
			rankInfo[i] = new RankInfo();
			rankInfo[i].rankNo = i + 1 + "";
		}
		try {
			// 1ʵ����
			KXmlParser parser = new KXmlParser();
			parser.setInput(is, "utf-8");

			// 2�����¼�����
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {// ���¼����Ͳ�Ϊ�ĵ�ĩβʱ
				switch (eventType) {// �ж��¼�����
				case XmlPullParser.START_TAG:// Ϊ��ʼ���ʱ-�պϱ�ǩ��ʼS
					// System.out.print("<" + parser.getName()+">");
					if ("rankItv".equals(parser.getName())) {// rankItv�˺�,rank����
						parser.next();
						rankInfo[arrIndex].userId = parser.getText();
						temp++;
					}
					if ("rank".equals(parser.getName())) {// rankItv�˺�,rank����
						parser.next();
						rankInfo[arrIndex].score = parser.getText();
						temp++;
					}
					if (temp != 0 && temp % 2 == 0) {
						arrIndex++;
					}
					break;

				case XmlPullParser.END_TAG: // Ϊ�������ʱ-�պϱ�ǩ����E
					// System.out.print("</" + parser.getName() + ">");
					break;

				case XmlPullParser.TEXT:// Ϊ�ĵ�����ʱ
					// System.out.print(parser.getText());
					break;

				case XmlPullParser.END_DOCUMENT:
					return rankInfo;
				}
				eventType = parser.next();
			}
			// System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rankInfo;
	}

	public class RankInfo {
		public String userId;
		public String rankNo;
		public String score;
	}

	/**
	 * HTTP Response��װ��
	 */
	public class RespWapper {
		String _code;
		/** �����Ŀ */
		String _goldCount;

		public String get_goldCount() {
			return _goldCount;
		}

		public void set_goldCount(String _goldCount) {
			this._goldCount = _goldCount;
		}

		String _unknown;

		public String get_unknown() {
			return _unknown;
		}

		public void set_unknown(String _unknown) {
			this._unknown = _unknown;
		}

		public String get_code() {
			return _code;
		}

		public void set_code(String _code) {
			this._code = _code;
		}

		String crossCodePoints;
		String levels;
		/** ���ؽ����true����ɹ���false����ʧ�� */
		boolean _ret;

		/** ������Ϣ���� */
		String _message;

		/** ��Ϸʱ�� */
		String _time;

		public String get_time() {
			return _time;
		}

		public void set_time(String _time) {
			this._time = _time;
		}

		/** ����������ʱ���� */
		InputStream _is;

		/** ���а��б������10 */
		RankInfo[] list;

		/** �ҵ����� */
		String myRankNo;

		/** �ҵĻ��� */
		String myScore;

		public boolean is_ret() {
			return _ret;
		}

		public void set_ret(boolean _ret) {
			this._ret = _ret;
		}

		public String get_message() {
			return _message;
		}

		public void set_message(String _message) {
			this._message = _message;
		}

		public InputStream get_is() {
			return _is;
		}

		public void set_is(InputStream _is) {
			this._is = _is;
		}

		public RankInfo[] getList() {
			return list;
		}

		public void setList(RankInfo[] list) {
			this.list = list;
		}

		public String getCrossCodePoints() {
			return crossCodePoints;
		}

		public void setCrossCodePoints(String crossCodePoints) {
			this.crossCodePoints = crossCodePoints;
		}

		public String getLevels() {
			return levels;
		}

		public void setLevels(String levels) {
			this.levels = levels;
		}

		public String getMyRankNo() {
			return myRankNo;
		}

		public void setMyRankNo(String myRankNo) {
			this.myRankNo = myRankNo;
		}

		public String getMyScore() {
			return myScore;
		}

		public void setMyScore(String myScore) {
			this.myScore = myScore;
		}

		RespWapper() {
		}

		RespWapper(boolean ret, String message, String code, String goldCount) {
			_ret = ret;
			_code = code;
			_message = message;
			_goldCount = goldCount;
			if (false == ret) {
				System.out.println("message:" + message);
			}
		}

		RespWapper(boolean ret, String message, InputStream is) {
			_ret = ret;
			_message = message;
			_is = is;
		}
	}
}
