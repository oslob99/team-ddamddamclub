import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/CompanyMain.scss'
import searchIcon from "../../src_assets/search-icon.png";
import {GrPowerReset} from "react-icons/gr";
import {Link, useNavigate} from "react-router-dom";
import {getToken} from "../common/util/login-util";

const CompanyMain = ({onSearchChange, onSearchKeywordChange, onSearchCareerChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('전체');
    const inputVal = useRef();
    const careerVal = useRef();

    //로그인 검증
    const ACCESS_TOKEN = getToken();
    const redirection = useNavigate();
    const handleInputChange = (e) => {
        const value = e.target.closest('.frontend-filter');
        if (value) {
            onSearchChange("front");
            setSelectedBtn("front");
        } else {
            const value = e.target.closest('.backend-filter');
            if (value) {
                onSearchChange("back");
                setSelectedBtn("back");
            } else {
                onSearchChange("");
                setSelectedBtn("");
            }
        }
    }
    const searchHandler = (e) => {
        onSearchKeywordChange(e.target.value);

        //엔터
        if (e.keyCode === 13) {
            onSearchKeywordChange(e.target.value);
        }
        if (e.target.value === '') {
            onSearchKeywordChange('');
        }
    }

    //리셋버튼
    const resetHandler = () => {
        inputVal.current.value = '';
        onSearchKeywordChange('');
        careerVal.current.value = '전체';
    }

    //경력 선택 버튼
    const careerHandler = (e) => {
        const careerValue = e.target.value;
        console.log(careerValue)
        onSearchCareerChange(careerValue);
    }


    return (
        <Common className={'company-top-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>채용공고</p>
                <p className={'main-sub-title'}>
                    땀땀클럽은 최신 채용 정보와 고용 관련을 위해 워크넷 API와 연동되었습니다.
                    <br/>다양한 직업 정보와 채용 소식을 신속하게 확인해보세요!
                </p>
            </div>

            <section className={'select-view-wrapper'}>
                <div className={'frontend-filter'} onClick={handleInputChange}>
                    <div className={'frontend-title'}>
                        <span className={'title-text'}>프론트엔드</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'frontend-count'}>12</span>
                </div>

                <div className={'backend-filter'} onClick={handleInputChange}>
                    <div className={'backend-title'}>
                        <span className={'title-text'}>백엔드</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'backend-count'}>12</span>
                </div>

                <div className={'total-filter'} onClick={handleInputChange}>
                    <div className={'total-title'}>
                        <span className={'title-text'}>개발직군 전체</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'total-count'}>12</span>
                </div>
            </section>

            {/*------------------------------------------------------------------------*/}
            <section className={'search-container'}>
                <div className={'search-wrapper'}>
                    <span className={'search-title'}>Search Keyword</span>
                    <div className={'search-input'}>
                        <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                        <input
                            className={'input-btn'}
                            placeholder={'검색창'}
                            name={'search'}
                            onKeyUp={searchHandler}
                            ref={inputVal}>
                        </input>
                    </div>
                </div>

                <div className={'select-wrapper'}>
                    <span className={'search-title'}>경력</span>
                    <select className={'select-career'} name={'userCareer'} defaultValue={'전체'} onChange={careerHandler}
                            ref={careerVal}>
                        <option value={'전체'}>전체</option>
                        <option value={'신입'}>신입</option>
                        <option value={'경력'}>경력</option>
                        <option value={'경력무관'}>경력무관</option>
                    </select>
                </div>
                <button className={'reset-btn'} onClick={resetHandler}><GrPowerReset/></button>
            </section>

        </Common>
        // <button className={'submit-btn'} onClick={submitHandler}>검색</button>
    );
};

export default CompanyMain;