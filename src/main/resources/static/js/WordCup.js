if(!window['lezhin']) {
	lezhin = {};
	( function (_O) {
    _O.start = () => {
      _O.Ctrl.gameNewStart.bind(_O.Ctrl)();
    };
    _O.Vars = {
      lists: [
        {
             name: '진돗개',
             imgSrc: '/images/1.jpg'
           },

           {
             name: '잭 러셀 테리어',
             imgSrc: '/images/2.jpg'
           },

           {
             name: '비숑프리제',
             imgSrc: '/images/3.jpg'
           },

           {
             name: '시베리안 허스키',
             imgSrc: '/images/4.jpg'
           },

           {
             name: '비글',
             imgSrc: '/images/5.jpg'
           },

           {
             name: '슈나우저',
             imgSrc: '/images/6.jpg'
           },

           {
             name: '시바견',
             imgSrc: '/images/7.jpg'
           },

           {
             name: '푸들',
             imgSrc: '/images/8.jpg'
           },

           {
             name: '프렌치 불도그',
             imgSrc: '/images/9.jpg'
           },

           {
             name: '래브라도 리트리버',
             imgSrc: '/images/10.jpg'
           },

           {
             name: '보더콜리',
             imgSrc: '/images/11.jpg'
           },

           {
             name: '셰퍼드',
             imgSrc: '/images/12.jpg'
           },

           {
             name: '퍼그',
             imgSrc: '/images/13.jpg'
           },

           {
             name: '닥스훈트',
             imgSrc: '/images/14.jpg'
           },

           {
             name: '골든리트리버',
             imgSrc: '/images/15.jpg'
           },

           {
             name: '웰시코기',
             imgSrc: '/images/16.jpg'
           },

           /*고양이*/
           {
             name: '스핑크스',
             imgSrc: '/images/17.jpg'
           },

           {
             name: '먼치킨 ',
             imgSrc: '/images/18.jpg'
           },

           {
             name: '코리안 숏헤어',
             imgSrc: '/images/19.jpg'
           },

           {
             name: '터키시 앙고라',
             imgSrc: '/images/20.jpg'
           },

           {
             name: '뱅갈 ',
             imgSrc: '/images/21.jpg'
           },

           {
             name: '엑죠틱 ',
             imgSrc: '/images/22.jpg'
           },

           {
             name: '아메리칸 숏헤어',
             imgSrc: '/images/23.jpg'
           },

           {
             name: '아비시니안',
             imgSrc: '/images/24.jpg'
           },

           {
             name: '브리티시 숏헤어',
             imgSrc: '/images/25.jpg'
           },

           {
             name: '랙돌 ',
             imgSrc: '/images/26.jpg'
           },

           {
             name: '스코티스 폴드',
             imgSrc: '/images/27.jpg'
           },

           {
             name: '러시안 블루',
             imgSrc: '/images/28.jpg'
           },

           {
             name: '노르웨이 숲',
             imgSrc: '/images/29.jpg'
           },

           {
             name: '메인쿤',
             imgSrc: '/images/30.jpg'
           },

           {
             name: '샤미즈',
             imgSrc: '/images/31.jpg'
           },

           {
             name: '페르시안',
             imgSrc: '/images/32.jpg'
           }
      ],
      curRound: 32,
      curStage: 0,
      gameHistory: {
        '32': [],
        '16': [],
        '8': [],
        '4': [],
        '2': [],
        '1': []
      }
    };
    _O.Ctrl = {
      rndLists(arr) { //배열 랜덤 섞음
        return arr.map((n) => { return [Math.random(), n] }).sort().map((n) => { n[1].selected = false; return n[1] });
      },
      selectedLists(arr) {
        return arr.filter((n) => {
          if(n.selected === true) {
            n.selected = false;
            return n;
          }
        });
      },
      gameNewStart() {
        const v = _O.Vars;
        v.gameHistory[v.curRound.toString()] = this.rndLists(v.lists);
        console.log('gameNewStart::gameHistory::', v.gameHistory);
        _O.Html.set.bind(_O.Html)();
      },

      nextRound() {
        const v = _O.Vars;
        if(v.curRound <= 1) return;
        if(v.curRound > 1) v.curRound /= 2;
        v.curStage = 0;
        v.lists = _O.Ctrl.selectedLists(v.gameHistory[(v.curRound * 2).toString()]);
        v.gameHistory[v.curRound.toString()] = this.rndLists(v.lists);
        _O.Html.setRoundTitle();
        _O.Html.setItem();
      }
    };
    _O.Event = {
      clickItem(obj) {
        if(_O.Vars.curRound === 1) return;
        const e = window.event;
        const idx = obj.id.split('_')[1];
        _O.Vars.gameHistory[_O.Vars.curRound.toString()][idx].selected = true;
        obj.className = 'item selected';
        if(_O.Vars.curStage < _O.Vars.curRound/2) _O.Vars.curStage++;
        if(_O.Vars.curStage === _O.Vars.curRound/2) _O.Ctrl.nextRound();
        _O.Html.setItem();
      }
    };
    _O.Html = {
      set() {
        this.setRoundTitle();
        this.setContent();
      },
      setRoundTitle() {
        if(_O.Vars.curRound > 1) document.getElementById('roundTitle').innerText = `${_O.Vars.curRound}강 선택`;
        else document.getElementById('roundTitle').innerText = `당신이 원하는 반려동물입니다~`;
      },
      setItem() {
        const s = _O.Html.getItem();
        const tObj = document.getElementById('list_ideal');
        if(!tObj) return;
        tObj.innerHTML = s;
      },
      getItem() {
        let s = '', i = _O.Vars.curStage * 2, length = i + (_O.Vars.curRound > 1 ? 2 : _O.Vars.curRound);
        for(i; i < length && length <= _O.Vars.curRound; i++) {
          s += `
          <li>
            <a class="item" id="item_${i}" href="javascript:void(0);" onclick="lezhin.Event.clickItem(this);">
              <span class="thumb"><img src="${_O.Vars.gameHistory[_O.Vars.curRound.toString()][i]['imgSrc']}"alt=반려동물사진"></span>
              <span class="tit"> ${_O.Vars.gameHistory[_O.Vars.curRound.toString()][i]['name']}</span>
            </a>
          </li>
          `;
        }
        return s;
      },
      setContent() {
        const tObj = document.getElementById('content');
        tObj.className = 'content in_game';
        let s = `
          <ul class="list_ideal" id="list_ideal">
          ${this.getItem()}
          </ul>
        `;
        tObj.innerHTML = s;
      }
    }
	}) (lezhin);
}